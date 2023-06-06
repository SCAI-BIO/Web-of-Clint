/**
 * Copyright 2023 Fraunhofer Institute SCAI, St. Augustin, Germany
 *
 * Licensed to Fraunhofer SCAI under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  Fraunhofer SCAI licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License. 
 *
 */
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
import de.fraunhofer.scai.bio.types.text.doc.structure.TextElement;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * some methods to create an RDF mapping file for annotations made by SCAIView
 * 
 * @author Marc Jacobs
 *
 */
/**
 * @author Marc Jacobs
 *
 */
@Slf4j public class AnnotationRDFBuilder {

    @Getter Model annotationsModel;

    public AnnotationRDFBuilder(Document doc) {
        this(doc, RDFUtils.parsingPrefixes());
    }

    /**
     * constructor - reqding a document and a prefix map
     * 
     * @param doc @see de.fraunhofer.scai.bio.Document
     */
    public AnnotationRDFBuilder(Document doc, Map<String, String> prefixes) {

        Concept c = doc.getDocumentElement().getMetaElement().getConcept(); 

        String id = c.getPreferredLabel().getText();
        log.info(" >> Working on {}", id);

        annotationsModel = ModelFactory.createDefaultModel();

        RDFUtils.addNSPrefixes(annotationsModel, prefixes);

        annotationsModel.add(
                RDFUtils.createProvenance(
                        prefixes.get(
                                c.getIdentifierSource().getText()) 
                        + RDFUtils.DOCUMENT 
                        + c.getIdentifier().getText().substring(0, c.getIdentifier().getText().lastIndexOf("_"))
                        )
                );

        for(Entry<Annotation, TextElement> e : DocumentUtils.extractAnnotations(doc).entrySet()) {
            annotationsModel.add(
                    createModel(e.getKey(), e.getValue(), prefixes)
                    );
        }
    }

    /**
     * constructor 
     * 
     * @param annotation @see de.fraunhofer.scai.bio.types.text.doc.meta.Annotation
     * @param elem @see de.fraunhofer.scai.bio.types.text.doc.structure.TextElement
     * @param prefixes 
     */
    public AnnotationRDFBuilder(Annotation annotation, TextElement elem, Map<String, String> prefixes) {

        log.info(" >> Working on {}:{}", annotation.getAnnotationType(), annotation.getAnnotationText());

        annotationsModel = createModel(annotation, elem, prefixes);
        annotationsModel.add(RDFUtils.createProvenance(annotation.getProvenance().getCollection()));
    }

    /**
     * 
     *       <http://example.org/anno13> a oa:Annotation ;
     *       oa:hasBody <http://example.org/review1> ;
     *       oa:hasTarget [
     *           oa:hasSource <http://example.org/ebook1> ;
     *           oa:hasSelector [
     *               a oa:TextPositionSelector ;
     *               oa:start 412 ;
     *               oa:end 795 ] ] .
     *               
     * @param annotation @see Annotation
     * @param elem @see TextElement
     * @param prefixes 
     * @return
     */
    private Model createModel(Annotation annotation, TextElement elem, Map<String, String> prefixes) {
        Model model = ModelFactory.createDefaultModel();
        
        Resource source = model.createResource(RDFUtils.WHO_URL+RDFUtils.TEXT_ELEMENT+elem.getUuid());
        source.addProperty(RDF.type, model.createResource("http://schema.org/Text"));
       
        Resource select = model.createResource(); // blank
        Resource target = model.createResource(); // blank

        Resource doc = model.createResource(annotation.getProvenance().getCollection());
        doc.addProperty(RDF.type, model.createResource("https://schema.org/TextDigitalDocument"));
        doc.addProperty(model.createProperty(DC_11.NS, "hasPart"), source);        

        Resource anot = model.createResource(prefixes.get(annotation.getAnnotationType())+annotation.getAnnotationText());
        RDFUtils.createAnnotationClass(model, anot, annotation);
        anot.addProperty(DC_11.identifier, RDFUtils.getCurl(annotation));   //"id": 401863018,    
        anot.addProperty(model.createProperty("http://www.w3.org/ns/oa#", "hasSource"), source);        
        anot.addProperty(model.createProperty("http://www.w3.org/ns/oa#", "hasBody"), doc);
        anot.addProperty(model.createProperty("http://www.w3.org/ns/oa#", "hasTarget"), target);

        target.addProperty(model.createProperty("http://www.w3.org/ns/oa#", "hasSelector"), select);
        source.addProperty(RDFS.label, model.createLiteral(elem.getText()));

        String lang = null;
        String label = elem.getText().substring(annotation.getStartOffset(), annotation.getEndOffset());

        if(annotation.getProvenance().getComments() != null) {
            for(String comment : annotation.getProvenance().getComments()) {
                if(comment.startsWith(RDFUtils.LANGUAGE_CODE)) {  
                    lang = comment.substring(RDFUtils.LANGUAGE_CODE.length()); 
                }
            }
        }

        select.addProperty(RDF.type, model.createProperty("http://www.w3.org/ns/oa#", "TextPositionSelector"));
        select.addLiteral(model.createProperty("http://www.w3.org/ns/oa#", "start"), annotation.getStartOffset());
        select.addLiteral(model.createProperty("http://www.w3.org/ns/oa#", "end"), annotation.getEndOffset());

        if(lang == null) {
            select.addProperty(RDFS.label, model.createLiteral(label));
        } else {
            select.addProperty(RDFS.label, model.createLiteral(label, lang));            
        }

        return model;
    }
}
