package de.fraunhofer.scai.bio.eiostooling.rdf;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DC_11;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import de.fraunhofer.scai.bio.Document;
import de.fraunhofer.scai.bio.eiostooling.doctypesystem.DocumentUtils;
import de.fraunhofer.scai.bio.types.text.doc.meta.Annotation;
import de.fraunhofer.scai.bio.types.text.doc.meta.Concept;
import de.fraunhofer.scai.bio.types.text.doc.meta.Date;
import de.fraunhofer.scai.bio.types.text.doc.meta.Title;
import de.fraunhofer.scai.bio.types.text.doc.structure.TextElement;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j public class MentioningBuilder {

    @Getter Model mentionsModel;
    @Setter private boolean addDayNode = false;

    /**
     * @param doc
     * @param addProvenance 
     */
    public MentioningBuilder(Document doc, boolean addProvenance) {
        this(doc, RDFUtils.parsingPrefixes(), addProvenance);
    }

    /**
     * create a bipartite graph of mentions and concepts mentioned
     * @param doc
     * @param prefixes
     * @param addProvenance 
     */
    public MentioningBuilder(Document doc, Map<String, String> prefixes, boolean addProvenance) {
        Concept c = doc.getDocumentElement().getMetaElement().getConcept(); 

        String id = c.getPreferredLabel().getText();
        log.debug(" >> Working on {}", id);

        mentionsModel = ModelFactory.createDefaultModel();

        RDFUtils.addNSPrefixes(mentionsModel, prefixes);
        
        String dataset = prefixes.get(c.getIdentifierSource().getText()) 
                + RDFUtils.DOCUMENT 
                + c.getIdentifier().getText().substring(0, c.getIdentifier().getText().lastIndexOf("_"));

        Resource eiosItem = mentionsModel.createResource(dataset);
        eiosItem.addProperty(RDF.type, mentionsModel.createResource("https://schema.org/TextDigitalDocument"));
        eiosItem.addProperty(DC_11.identifier, "EIOS:" + doc.getDocumentElement().getMetaElement().getConcept().getIdentifier().getText());

        if (doc.getDocumentElement().getMetaElement().getBibliographic().getTitle() != null) {
            Title title= doc.getDocumentElement().getMetaElement().getBibliographic().getTitle();
            
            if(!title.getTitleText().getText().isEmpty()) {
                eiosItem.addProperty(RDFS.label, title.getTitleText().getText(), 
                        doc.getDocumentElement().getMetaElement().getBibliographic().getLanguage().getText());                
            }
        }

        
        if(addProvenance) {
            mentionsModel.add(
                    RDFUtils.createProvenance( dataset )
                    );
        }
        
        Date date = doc.getDocumentElement().getMetaElement().getBibliographic().getPubDate();
        
        for(Entry<Annotation, TextElement> e : DocumentUtils.extractAnnotations(doc).entrySet()) {
            mentionsModel.add(
                    createModel(e.getKey(), e.getValue(), prefixes, date, eiosItem)
                    );
        }

        String sparql =   
                "PREFIX EIOS: <https://www.scai.fraunhofer.de/EiosTooling/> "
                + "SELECT ?id1 ?l1 ?c1 ?id2 ?l2 ?d " 
                + "WHERE { "
                + "?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?c1 . "
                + "?s <" + RDFS.label +"> ?l1 . " 
                + "?s <" + DC_11.identifier + "> ?id1 . "
                + "?s EIOS:mentionedIn ?o . "
                + "?o  <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?c2 . "
                + "?o <" + RDFS.label +"> ?l2 . " 
                + "?o <" + DC_11.identifier + "> ?id2 . "
                + "?o EIOS:publishedAt ?d . "                
                + "}" 
                ;
        
        if(log.isDebugEnabled()) {
            RDFUtils.queryModel(mentionsModel, sparql);
        }
    }

    /**
     * @param annotation
     * @param elem
     * @param prefixes
     * @param eiosItem 
     * @return
     */
    private Model createModel(Annotation annotation, TextElement elem, Map<String, String> prefixes, Date date, Resource eiosItem) {
        Model model = ModelFactory.createDefaultModel();

        String dateStr = String.format("%04d-%02d-%02d", date.getYear(), date.getMonth(), date.getDay());
        
        Resource mentioning = model.createResource(RDFUtils.WHO_URL+RDFUtils.TEXT_ELEMENT+elem.getUuid());
        mentioning.addProperty(RDF.type, model.createResource("https://www.scai.fraunhofer.de/EiosTooling/Mentioning"));
        mentioning.addProperty(RDFS.label, model.createLiteral(elem.getText()));
        mentioning.addProperty(DC_11.identifier, mentioning.getLocalName());   //"id": 401863018,    
        mentioning.addProperty(model.createProperty("https://www.scai.fraunhofer.de/EiosTooling/", "partOf"), eiosItem);
        
        if(addDayNode ) {
            Resource day = model.createResource(RDFUtils.WHO_URL+RDFUtils.DAY+dateStr);
            day.addProperty(RDF.type, model.createResource("https://www.scai.fraunhofer.de/EiosTooling/Date"));
            day.addProperty(RDFS.label, model.createLiteral(dateStr));
            mentioning.addProperty(model.createProperty("https://www.scai.fraunhofer.de/EiosTooling/", "publishedAt"), day);        
        }
        
        Resource anot = model.createResource(prefixes.get(annotation.getAnnotationType())+annotation.getAnnotationText());
        RDFUtils.createAnnotationClass(model, anot, annotation);
        anot.addProperty(DC_11.identifier, RDFUtils.getCurl(annotation));   //"id": 401863018,    
        anot.addProperty(model.createProperty("https://www.scai.fraunhofer.de/EiosTooling/", "mentionedIn"), mentioning);        

        String lang = null;
        String label = elem.getText().substring(annotation.getStartOffset(), annotation.getEndOffset());

        if(annotation.getProvenance().getComments() != null) {
            for(String comment : annotation.getProvenance().getComments()) {
                if(comment.startsWith(RDFUtils.LANGUAGE_CODE)) {  
                    lang = comment.substring(RDFUtils.LANGUAGE_CODE.length()); 
                }
            }
        }

        if(lang == null) {
            anot.addProperty(RDFS.label, model.createLiteral(label));
        } else {
            anot.addProperty(RDFS.label, model.createLiteral(label, lang));            
        }

        return model;
    }
}
