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
package de.fraunhofer.scai.bio.eiostooling.doctypesystem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.fraunhofer.scai.bio.Document;
import de.fraunhofer.scai.bio.Provenance;
import de.fraunhofer.scai.bio.eiostooling.rdf.RDFUtils;
import de.fraunhofer.scai.bio.types.text.doc.container.BodyMatter;
import de.fraunhofer.scai.bio.types.text.doc.container.Chapter;
import de.fraunhofer.scai.bio.types.text.doc.container.FrontMatter;
import de.fraunhofer.scai.bio.types.text.doc.container.Paragraph;
import de.fraunhofer.scai.bio.types.text.doc.container.Section;
import de.fraunhofer.scai.bio.types.text.doc.container.StructureElement;
import de.fraunhofer.scai.bio.types.text.doc.meta.Annotation;
import de.fraunhofer.scai.bio.types.text.doc.meta.Author;
import de.fraunhofer.scai.bio.types.text.doc.meta.Bibliographic;
import de.fraunhofer.scai.bio.types.text.doc.meta.MetaElement;
import de.fraunhofer.scai.bio.types.text.doc.meta.PublicationType;
import de.fraunhofer.scai.bio.types.text.doc.structure.TextElement;
import intl.who.eios.item.Item;
import intl.who.eios.item.Location;
import intl.who.eios.item.Occurrence;
import intl.who.eios.item.Occurrence__1;
import intl.who.eios.item.Trigger;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * generating a 
 * @see de.fraunhofer.scai.bio.Document
 * from an EIOS Item
 * 
 * @author Marc Jacobs
 *
 */
@Slf4j public class DocumentBuilder {

    private static final String LANGUAGE_CODE = "LanguageCode:";
    private static final String EIOS = "EIOS";
    private static final String VERSION = "0.0.1-SNAPSHOT";
    private static final String LICENSE = "https://www.who.int/about/policies/terms-of-use";
    private static final String WHO = "World Health Organization (WHO)";
    private static final String TAGS = "category";
    private static final String LOCATION = "gn";
    public static final String PEOPLE = "dbpedia-person";
    public static final String ORGANIZATIONS = "dbpedia-organization";

    @Getter private Document documentOrig = null;
    @Getter private Document documentTranslated = null;
    @Getter private List<Annotation> annotations = new ArrayList<Annotation>();

    private de.fraunhofer.scai.bio.util.DocumentBuilder builder;
    private List<TextElement> textElements;

    public DocumentBuilder(Item item, int i) {
        
        this(item, i, parsingMappings());
    }
    
    /**
     * @param item
     */
    public DocumentBuilder(Item item, int i, Map<String, String> mappings) {
        
        textElements = new ArrayList<TextElement>();

        builder = new de.fraunhofer.scai.bio.util.DocumentBuilder();

        log.debug(" >> working on {} - {}", i, item.getId());
        documentOrig = createDocument(item, false, mappings);

        if(!item.getTranslatedTitle().isEmpty()) {
            documentTranslated = createDocument(item, true, mappings);            
        }

        log.debug(" >> done.");
    }

    /**
     * @param item
     * @param translated
     * @param mappings 
     * @return
     */
    private Document createDocument(Item item, boolean translated, Map<String, String> mappings) {
        Document document = new Document();
        document.setOriginalMimeType("Application/JSON");
        builder.setSource(document, WHO);
        builder.addOrganization(document, WHO, EIOS, "system");

        document.setProvenance(
                createProvenance(EIOS)
                );

        Bibliographic biblio = builder.getBibliographic(document);
        BodyMatter body = builder.getBodyMatter(document);
        FrontMatter front = builder.getFrontMatter(document);
        MetaElement meta = document.getDocumentElement().getMetaElement();

        setDocType(item, document, biblio);

        try {
            ZonedDateTime  date = ZonedDateTime.parse(item.getPubDate());  //2023-01-02T15:40:00Z
            builder.setPublicationDate(document,date.getDayOfMonth(), date.getMonthValue(), date.getYear());
        } catch (Exception ex) {
            
        }
        
        LocalDate pdate = null;
        
        try {
            pdate = ZonedDateTime.parse(item.getPubDate()).toLocalDate();  //2023-01-02T15:40:00Z
        } catch (Exception ex) {
            
        }
        
        ArrayList<Author> authors= new ArrayList<Author>();
        authors.add(builder.createAuthor(null, item.getSource().getName()));

        builder.addReference(document, "Origin", item.getSource().getUrl(), item.getSource().getId().toString(), item.getSource().getCategory(), item.getTitle(), authors , pdate);

        if(translated) {
            builder.setLanguage(document, "en");
            builder.setDocumentId(document, EIOS, item.getId().toString()+"_en", null);
            meta.getConcept().setPreferredLabel(
                    builder.createTextElement(
                            meta.getConcept().getPrefLabel().getText()));           

            biblio.setTitle(
                    builder.createTitle(item.getTranslatedTitle())            
                    );

            front.setTitleText(builder.createTextElement(item.getTranslatedTitle()));

            builder.setAbstract(document, item.getTranslatedDescription()); //item.getAbstractiveSummary());

            body.addSection(
                    builder.createMainSection(document, item.getTranslatedText())
                    );     

        } else {
            builder.setLanguage(document, item.getLanguageCode());
            builder.setDocumentId(document, EIOS, item.getId().toString()+"_"+item.getLanguageCode(), null);
            meta.getConcept().setPreferredLabel(
                    builder.createTextElement(
                            meta.getConcept().getPrefLabel().getText()));

            biblio.setTitle(
                    builder.createTitle(item.getTitle())            
                    );
            front.setTitleText(builder.createTextElement(item.getTitle()));

            body.addSection(
                    builder.createMainSection(document, item.getFullText())
                    );
            builder.setAbstract(document, item.getDescription()); //item.getAbstractiveSummary());        
        }

        createKeywords(item, meta);
        createAnnotations(item, body, front, mappings);

        return document;
    }

    private void createAnnotations(Item item, BodyMatter body, FrontMatter front, Map<String, String> mappings) {
        textElements.add(front.getTitleText());
        gatherTextElements(front.getDocumentAbstract().getAbstractSections());
        if(body.getChapters()!= null) {
            for(Chapter chap : body.getChapters()) {
                gatherTextElements(chap.getSections());
            }
        }
        gatherTextElements(body.getSections()); 

        // create trigger annotations
        if(item.getTriggers() != null) {
            for(Trigger trigger : item.getTriggers()) {
                if(trigger.getKey().startsWith("l:")) {
                    log.debug(" >> trigger {}: {}", trigger.getKey(), trigger.getValues());
                    for(String text : trigger.getValues()) {
                        
                        String identifier = trigger.getKey().substring(2);
                        String type = TAGS;
                        
                        if(mappings.containsKey(identifier)) {
                            String curie = mappings.get(identifier);  //  PCO:0000033
                            String[] parts = curie.split(":");
                            if(parts.length>1) {
                                type = parts[0];
                                identifier = "";
                                for(int i=1; i<parts.length; i++) {
                                    identifier += (":"+parts[i]);    
                                }
                                identifier = identifier.substring(1);
                            }
                        }
                        
                        mapAnnotation(
                                RDFUtils.WHO_URL+RDFUtils.DOCUMENT+item.getId(), textElements, identifier, text, type, item.getLanguageCode()
                                );
                    }
                }
            }
        }

        if(item.getLocations() != null) {
            for(Location loc : item.getLocations()) {
                if(!loc.getTrigger().isEmpty() && loc.getArea() != null && !loc.getArea().getId().isEmpty()) {
                    log.debug(" >> location {}: {}", loc.getTrigger(), loc.getArea().getId());
                    mapAnnotation(
                            RDFUtils.WHO_URL+RDFUtils.DOCUMENT+item.getId(), textElements, loc.getArea().getId().substring(9)+"/", loc.getTrigger(), LOCATION, item.getLanguageCode()
                            );                
                }
            }
        }

        if(item.getMentionedPeople() != null && item.getMentionedPeople().getOccurrences() != null) {
            for(Occurrence people :  item.getMentionedPeople().getOccurrences()) {
                log.debug(" >> person {}: {}", people.getName(), people.getTrigger());
                mapAnnotation(
                        RDFUtils.WHO_URL+RDFUtils.DOCUMENT+item.getId(), textElements, people.getName().replaceAll("\\s+", "_"), people.getTrigger(), PEOPLE, item.getLanguageCode()
                        );                

                // https://lookup.dbpedia.org/api/search?format=JSON&type=Person&query=Ana%20Rosa
                // {"docs":[{"score":["4996.6997"],"refCount":["3"],"resource":["http://dbpedia.org/resource/Ana_Rosa_Payán"],
                // https://lookup.dbpedia.org/api/search?query=Ana%20Rosa
                // <URI>http://dbpedia.org/resource/Ana_Rosa_Payán</URI>
            }
        }

        if(item.getMentionedOrganisations() != null && item.getMentionedOrganisations().getOccurrences() != null) {
            for(Occurrence__1 organization :  item.getMentionedOrganisations().getOccurrences()) {
                log.debug(" >> organization {}: {}", organization.getName(), organization.getTrigger());
                mapAnnotation(
                        RDFUtils.WHO_URL+RDFUtils.DOCUMENT+item.getId(), textElements, organization.getName().replaceAll("\\s+", "_"), organization.getTrigger(), ORGANIZATIONS, item.getLanguageCode()
                        );        

                // https://lookup.dbpedia.org/api/search?type=Organisation&query=Mediaset
                // http://dbpedia.org/resource/Mediaset
            }
        }

    }

    private void setDocType(Item item, Document document, Bibliographic biblio) {
        PublicationType publicationType = new PublicationType();
        publicationType.setIdentifier(builder.createTextElement("D018431"));
        publicationType.setPublicationType(builder.createTextElement(item.getSource().getSubject()));
        biblio.addPublicationType(publicationType);    
        builder.setDocType(document, item.getSource().getSubject());
    }

    /**
     * @param sections
     */
    private void gatherTextElements(List<Section> sections) {
        if(sections != null) {
            for(Section sec : sections) {
                if(sec.getParagraphs() != null) {
                    for(Paragraph p : sec.getParagraphs()) {
                        if(p != null && p.getStructureElements() != null) {
                            for(StructureElement se : p.getStructureElements()) {
                                if(se.getTextElement() != null) {
                                    textElements.add(se.getTextElement());
                                }
                                if(se.getSentence() != null) {
                                    if(se.getSentence().getSentenceText() != null) {
                                        textElements.add(se.getSentence().getSentenceText());
                                    }
                                    if(se.getSentence().getText() != null) {
                                        textElements.add(se.getSentence().getText());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void createKeywords(Item item, MetaElement meta) {
        List<String> tags = new ArrayList<String>();
        for (String tag : item.getTags()) { //."tags": ["l:Death", "f:18:Outcomes", "f:18:All JRC Categories", "f:31:Outcomes", "f:31:All UNODC Categories", "l:EMRO", "l:EURO"
            if(!tag.isEmpty() && tag.startsWith("l:")) {
                tags.add(tag.substring(2));
            }
        }

        if(!tags.isEmpty()) {
            meta.addKeywords(
                    builder.createKeywords(tags, "tags", true)
                    );
        }

        if(!item.getAffectedCountries().isEmpty()) {
            meta.addKeywords(
                    builder.createKeywords(item.getAffectedCountries(), "affected countries", true)
                    );
        }
        if(!item.getWhoRegionCodes().isEmpty()) {
            meta.addKeywords(
                    builder.createKeywords(item.getWhoRegionCodes(), "WHO region codes", true)
                    );
        }
        if(!item.getContinentCodes().isEmpty()) {
            meta.addKeywords(
                    builder.createKeywords(item.getContinentCodes(), "continent codes", true)
                    );
        }       
        if(item.getMentionedOrganisations() != null && !item.getMentionedOrganisations().getNames().isEmpty()) {
            meta.addKeywords(
                    builder.createKeywords(item.getMentionedOrganisations().getNames(), "mentioned organizations", true)
                    );            
        }

        if(!item.getLocations().isEmpty()) {
            List<String> locations = new ArrayList<String>();
            for(Location loc : item.getLocations()) {
                try {
                    locations.add(loc.getTrigger());
                } catch (Exception ex) {
                    // TODO   
                }
            }
            if(!locations.isEmpty()) {
                meta.addKeywords(
                        builder.createKeywords(locations, "locations", true)
                        );
            }
        }
    }

    /**
     * @param collection 
     * @param elements
     * @param identifier
     * @param text
     * @param lang 
     */
    private void mapAnnotation(String collection, List<TextElement> elements, String identifier, String text, String type, String lang) {
        for(TextElement elem : elements) {
            int idx = elem.getText().indexOf(text);
            if(idx >= 0) {
                elem.addAnnotation(
                        createAnnotation(collection, identifier, idx, idx+text.length(), type, lang)
                        );
            }
        }
    }

    /**
     * @param collection 
     * @param identifier
     * @param begin
     * @param end
     * @param lang 
     * @return
     */
    private Annotation createAnnotation(String collection, String identifier, int begin, int end, String type, String lang) {
        Annotation anot = new Annotation();

        anot.setAnnotationText(identifier);
        anot.setAnnotationType(type);
        anot.setStartOffset(begin);
        anot.setEndOffset(end);
        anot.setProvenance(createAnnoationProvenance(collection, type, lang));

        log.debug(" >> annotated {}", anot);

        return anot;
    }

    /**
     * @param collection
     * @return
     */
    private Provenance createProvenance(String collection) {
        Provenance prov = new Provenance();
        prov.setCollection(collection);
        prov.setDate(new Date());
        prov.setLicense(LICENSE);
        prov.setSource(RDFUtils.WHO_URL+EIOS);
        prov.setSourceLabel(EIOS);
        prov.setVersion(VERSION);

        return prov;
    }

    private Provenance createAnnoationProvenance(String collection, String tagger, String lang) {
        List<String> comments = new ArrayList<String>();
        comments.add(LANGUAGE_CODE+lang);

        Provenance prov = new Provenance();
        prov.setCollection(collection);
        prov.setDate(new Date());
        prov.setLicense("https://creativecommons.org/licenses/by/4.0/deed.de");
        prov.setSource(RDFUtils.WHO_URL+EIOS+"_"+tagger);
        prov.setSourceLabel(EIOS);
        prov.setVersion(VERSION);
        prov.setComments(comments);

        return prov;
    }


    /**
     * @param name
     * @param doc
     * @throws IOException
     */
    public void writeToFile(String name, Document doc) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString( doc );

        FileUtils.writeStringToFile(new File(name), json, Charset.forName("UTF-8"));
    }


    /**
     * @param name
     * @param doc
     * @throws IOException
     */
    public String writeToJSON(Document doc) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString( doc );
    }

    /**
     * 
     */
    static public Map<String, String> parsingMappings() {
        TreeMap<String, String> mappings = new TreeMap<String, String>();

        try {
            log.debug(" >>> parsing EIOS mappings...");

            InputStream in = new ClassPathResource("curie.csv").getInputStream();

            try {
                CSVParser parser = CSVParser.parse(in, Charset.forName("UTF-8"), CSVFormat.DEFAULT.withAllowMissingColumnNames());
                
                log.debug("{}", parser.getHeaderNames());
                
                parser.forEach(record -> {
                    if(record.isConsistent() && record.size() == 5) {
                        if(!record.get(1).isEmpty() && !record.get(4).isEmpty()) {
                            mappings.put(record.get(1), record.get(4));
                        }
                    } else {
                        log.error("invalid record: {}", record.toString());
                    }
                });
            } catch (Exception e) {
                log.error(" >>> wrong prefixes.tsv file");
                log.debug(e.getLocalizedMessage(), e);
            }

            in.close();
            
            log.debug(" >>> got {} mappings.", mappings.size());
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            log.debug(e.getLocalizedMessage(), e);
        }
        
        return mappings;
    }

}
