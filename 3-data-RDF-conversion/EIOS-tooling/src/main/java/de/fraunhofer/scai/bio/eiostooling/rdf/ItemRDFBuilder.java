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

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.DC_11;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.PROV;

import intl.who.eios.item.EiosQueryResult;
import intl.who.eios.item.Hit;
import intl.who.eios.item.Item;
import intl.who.eios.item.Location;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * some utilities to build an RDFmodel from an EIOS item
 *
 * @author Marc Jacobs
 */

@Slf4j
public class ItemRDFBuilder {

    @Getter
    Map<String, Model> documentsModel;

    Model voidModel;

    public ItemRDFBuilder(Item item) {

        // documentsModel.add(createProvenance(item, 0));
        documentsModel.put("item_" + item.getId().toString(), createDocument(item, 0, null));
    }

    public ItemRDFBuilder(EiosQueryResult result) {

        documentsModel = new HashMap<String, Model>();

        // createProvenance(hit.getSource());

        Map<String, String> prefixes = RDFUtils.parsingPrefixes();

        int i = 0;
        for (Hit hit : result.getHits().getHits()) {
            documentsModel.put("item_" + hit.getId().toString(), createDocument(hit.getSource(), ++i, prefixes));
        }
    }

    /**
     * @param item
     * @return
     */
    public Model createProvenance(Item item) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));

        Model model = ModelFactory.createDefaultModel();

        Resource eiosItem = model.createResource(RDFUtils.WHO_URL + RDFUtils.DOCUMENT + item.getId());
        eiosItem.addProperty(RDF.type, model.createResource(PROV.ENTITY.stringValue()));
        eiosItem.addProperty(RDFS.label, item.getId().toString());

        Resource scai = model.createResource(RDFUtils.SCAI_URL);
        scai.addProperty(RDF.type, model.createResource(PROV.ORGANIZATION.stringValue()));
        scai.addProperty(RDFS.label,
                model.createLiteral("Fraunhofer Institute of Algorithms and Scientific Computing (SCAI)", "en"));

        Resource who = model.createResource(RDFUtils.WHO_URL);
        who.addProperty(RDF.type, model.createResource(PROV.ORGANIZATION.stringValue()));
        who.addProperty(RDFS.label, model.createLiteral("World Health Organization (WHO)", "en"));

        Resource eiosTooling = model.createResource("https://www.scai.fraunhofer.de/EiosTooling");
        // eiostooling.addProperty(RDF.type, DCTerms.ProvenanceStatement);
        eiosTooling.addProperty(RDF.type, model.createResource("http://www.w3.org/ns/prov#SoftwareAgent"));
        eiosTooling.addProperty(RDFS.label, model.createLiteral("EIOS Tooling", "en"));
        eiosTooling.addProperty(model.createProperty(PROV.AT_TIME.stringValue()), model.createTypedLiteral(cal));
        eiosTooling.addProperty(model.createProperty(PROV.GENERATED.stringValue()), eiosItem);
        eiosTooling.addProperty(model.createProperty(PROV.NAMESPACE, "actedOnBehalfOf"), scai);

        Resource eiosETL = model.createResource("https://www.scai.fraunhofer.de/EiosTooling/RDFBuilder");
        eiosETL.addProperty(RDF.type, model.createResource(PROV.ACTIVITY.stringValue()));
        eiosETL.addProperty(RDFS.label, model.createLiteral("conversion EIOS to RDF", "en"));
        eiosETL.addProperty(model.createProperty(PROV.USED.stringValue()), eiosItem);
        eiosETL.addProperty(model.createProperty(PROV.WAS_ASSOCIATED_WITH.stringValue()), eiosTooling);

        eiosItem.addProperty(model.createProperty(PROV.WAS_ATTRIBUTED_TO.stringValue()), eiosTooling);
        eiosItem.addProperty(model.createProperty(PROV.WAS_DERIVED_FROM.stringValue()),
                model.createResource(item.getLink()));
        eiosItem.addProperty(model.createProperty(PROV.WAS_GENERATED_BY.stringValue()), eiosETL);
        eiosItem.addProperty(model.createProperty(PROV.NAMESPACE, "wasGeneratedBy"), who);

        return model;
    }

    /**
     * @param item
     * @param prefixes
     * @return
     */
    public Model createDocument(Item item, int i, Map<String, String> prefixes) {
        log.debug(" >> Working on {} - {}", i, item.getId());

        Model model = ModelFactory.createDefaultModel();
        RDFUtils.addNSPrefixes(model, prefixes);

        Resource eiosItem = model.createResource(RDFUtils.EIOS + RDFUtils.DOCUMENT + item.getId()); // "id":
        // 401863018,
        eiosItem.addProperty(RDF.type, model.createResource("https://schema.org/TextDigitalDocument"));
        eiosItem.addProperty(DC_11.creator, model.createResource("https://www.who.int/initiatives/eios"));
        eiosItem.addProperty(DC_11.format, "application/json");
        eiosItem.addProperty(DC_11.identifier, "EIOS:" + item.getId().toString()); // "id": 401863018,

        if (item.getTitle() != null && !item.getTitle().isEmpty())
            eiosItem.addProperty(DC_11.title, item.getTitle(), item.getLanguageCode()); // "title": "Nach zehnmonatiger
        // Verhandlung: Urteil im
        // Prozess um Mord an Maryam. H
        // in Sicht",
        if (item.getTranslatedTitle() != null && !item.getTranslatedTitle().isEmpty())
            eiosItem.addProperty(DC_11.title, item.getTranslatedTitle(), "en"); // "translatedTitle": "After ten month
        // sodium negotiation: judgment in the
        // process to murder of Maryam. H in
        // sight",
        if (item.getDescription() != null && !item.getDescription().isEmpty())
            eiosItem.addProperty(DC_11.description, item.getDescription(), item.getLanguageCode()); // "description":
        // "Im Prozess um
        // den gewaltsamen
        // Tod einer

        if (item.getTranslatedDescription() != null && !item.getTranslatedDescription().isEmpty())
            eiosItem.addProperty(DC_11.description, item.getTranslatedDescription(), item.getLanguageCode()); // "translatedDescription":
        // "In the
        // process
        // the
        // violent
        // death

        if (item.getPubDate() != null && !item.getPubDate().isEmpty())
            eiosItem.addProperty(DC_11.date, item.getPubDate()); // "pubDate": "2023-01-02T15:40:00Z",
        if (item.getLink() != null && !item.getLink().isEmpty())
            eiosItem.addProperty(DC_11.source, model.createResource(item.getLink())); // "link":
        // "https://www.tagesspiegel.de/berlin/nach-zehnmonatiger-verhandlung-urteil-im-prozess-um-mord-an-maryam-h-in-sicht-9115525.html",
        if (item.getLanguage() != null && !item.getLanguage().isEmpty())
            eiosItem.addProperty(DC_11.language, item.getLanguage());
        if (item.getDuplicateId() != null)
            eiosItem.addProperty(DC_11.relation, item.getDuplicateId()); // "duplicateId":
        // "abendzeitung-muenchen-bc58c82dd30169722a4543867c9fb9d6",
        if (item.getFullText() != null && !item.getFullText().isEmpty())
            eiosItem.addProperty(model.createProperty("http://schema.org/", "text"), item.getFullText(),
                    item.getLanguageCode()); // "fullText": "Im Prozess um den gewaltsamen Tod einer Afghanin gegen zwei
        // Brüder des 34-jährigen Opfers ist nach Planungen des Berliner
        // Landgerichts ein Urteil in Sicht. Für den 9. Januar sind nach
        // derzeitigem Stand die Plädoyers von Staatsanwaltschaft, Nebenklage und

        if (!item.getTranslatedText().isEmpty())
            eiosItem.addProperty(model.createProperty("http://schema.org/", "text"), item.getTranslatedText(), "en"); // get"translatedText":
        // "",

        if (item.getTags() != null) {
            for (String tag : item.getTags()) { // ."tags": ["l:Death", "f:18:Outcomes", "f:18:All JRC Categories",
                // "f:31:Outcomes", "f:31:All UNODC Categories", "l:EMRO", "l:EURO"
                if (!tag.isEmpty() && tag.startsWith("l:")) {
                    eiosItem.addProperty(DC_11.subject, tag.substring(2));
                }
            }
        }

        if (item.getAffectedCountries() != null && !item.getAffectedCountries().isEmpty()) {
            for (String ac : item.getAffectedCountries()) { // "AF:Afghanistan", "DE:Germany" ],
                eiosItem.addProperty(DC_11.subject, ac);
            }
        }
        if (item.getAffectedCountriesIso() != null && !item.getAffectedCountriesIso().isEmpty())
            item.getAffectedCountriesIso(); // "affectedCountriesIso": [ "AF","DE" ],
        if (item.getEmmMisInfo() != null)
            item.getEmmMisInfo(); // "emmMisInfo": { "label": "reliable", "confidenceScore": 0.0002

        if (item.getRssItemId() != null && !item.getRssItemId().isEmpty()) {
            Property derived = model.createProperty(PROV.WAS_DERIVED_FROM.stringValue());

            Resource rss = model.createResource(RDFUtils.WHO_URL+item.getRssItemId());  // "rssItemId": "Tagespiegel-efa6a489d5574a21471bc32d32d82212", 
            rss.addProperty(RDF.type, model.createResource("https://schema.org/webFeed"));
            eiosItem.addProperty(derived, rss);

            if (item.getParentId() != null) {
                Resource parent = model.createResource(RDFUtils.WHO_URL+item.getParentId());  // "parentId": "abendzeitung-muenchen-bc58c82dd30169722a4543867c9fb9d6",
                parent.addProperty(RDF.type, "https://schema.org/webFeed");
                rss.addProperty(derived, parent);
            }
        }

        if (item.getProcessedOnDate() != null && !item.getProcessedOnDate().isEmpty())
            item.getProcessedOnDate(); // ."processedOnDate": "2023-01-02T15:43:14.2855300Z",

        if (item.getLocations() != null && !item.getLocations().isEmpty()) {
            for (Location loc : item.getLocations()) {
                eiosItem.addProperty(DC_11.subject, loc.getTrigger());
            }
        }

        if (item.getWhoRegionCodes() != null && !item.getWhoRegionCodes().isEmpty()) {
            for (String rc : item.getWhoRegionCodes()) { // "whoRegionCodes": [ "EMR", "EUR"],
                eiosItem.addProperty(DC_11.subject, rc);
            }
        }

        if (item.getContinentCodes() != null && !item.getContinentCodes().isEmpty()) {
            for (String cc : item.getContinentCodes()) { // "continentCodes": ["AS", "EU"]
                eiosItem.addProperty(DC_11.subject, cc);
            }
        }

        if (item.getSource() != null && item.getSource().getUrl() != null) {
            if (!item.getSource().getUrl().isEmpty()) {
                try {
                    String pubUri = item.getSource().getUrl().replaceAll("\\s+", "");

                    RDFUtils.isValidURL(pubUri);

                    Resource publisher = model.createResource(pubUri); // "url": "http://www.tagesspiegel.de",
                    publisher.addProperty(RDF.type, DC_11.publisher);
                    publisher.addProperty(DC_11.identifier, item.getSource().getId().toString()); // "id": 236,
                    if (!item.getSource().getName().isEmpty())
                        publisher.addProperty(RDFS.label, item.getSource().getName(),
                                item.getSource().getLanguageCode()); // "name": "Der tagesspiegel",
                    if (item.getSource().getLanguage() != null)
                        publisher.addProperty(DC_11.language, item.getSource().getLanguageCode()); // "language":
                    // "German",
                    if (item.getSource().getFrequency() != null)
                        publisher.addProperty(DCTerms.accrualPeriodicity, item.getSource().getFrequency().toString()); // "frequency":
                    // 8,
                    if (item.getSource().getPeriod() != null && !item.getSource().getPeriod().isEmpty())
                        publisher.addProperty(DCTerms.accrualPeriodicity, item.getSource().getPeriod()); // "period":
                    // "daily",
                    if (item.getSource().getCategory() != null && !item.getSource().getCategory().isEmpty())
                        publisher.addProperty(DC_11.coverage, item.getSource().getCategory()); // "category":
                    // "National",
                    if (item.getSource().getLastUpdateDate() != null && !item.getSource().getLastUpdateDate().isEmpty())
                        publisher.addProperty(DC_11.date, item.getSource().getLastUpdateDate()); // "lastUpdateDate":
                    // "2019-10-16T12:47:10.5550000Z",
                    if (item.getSource().getName() != null && !item.getSource().getName().isEmpty())
                        publisher.addProperty(model.createProperty(FOAF.NAME.stringValue()), item.getSource().getName(),
                                item.getSource().getLanguageCode()); // "name": "Der tagesspiegel",

                    eiosItem.addProperty(DC_11.publisher, publisher);

                } catch (MalformedURLException | URISyntaxException e) {
                    log.error(" incorrect URI for pulbisher {}", item.getSource().getUrl());
                }

                if (item.getSource().getSubject() != null && !item.getSource().getSubject().isEmpty())
                    eiosItem.addProperty(DC_11.coverage, item.getSource().getSubject()); // "subject": "General News",
                if (item.getSource().getType() != null && !item.getSource().getType().isEmpty())
                    eiosItem.addProperty(DC_11.type, item.getSource().getType()); // "type": "webnews",
            }

            item.getSource().getOptimaSourceId();// "optimaSourceId": "Tagespiegel",
            item.getSource().getCountry(); // "country": "Germany",
            item.getSource().getCountryIso(); // "countryIso": "de",
            item.getSource().getLanguageFull(); // "languageFull": "de:German",
            item.getSource().getCountryFull(); // "countryFull": "de:Germany",
            item.getSource().getRegion(); // "region": "European Union",
            item.getSource().getVersion(); // "version": 1571230030555,
            item.getSource().getState(); // "state": "active",
            item.getSource().getLastUpdateDate(); // "lastUpdateDate": "2019-10-16T12:47:10.5550000Z",
            item.getSource().getContinentCode(); // "continentCode": "EU",
            item.getSource().getWhoRegionCode(); // "whoRegionCode": "EUR"
        }

        return model;
    }

    public Model createVoID() {

        Model model = ModelFactory.createDefaultModel();

        Property vocabulary = model.createProperty(RDFUtils.VOID_NS, "vocabulary");

        RDFUtils.addNSPrefixes(model, null);

        Resource eiosDataset = model.createResource(RDFUtils.WHO_URL + "EIOS");
        eiosDataset.addProperty(RDF.type, model.createProperty(RDFUtils.VOID_NS, "Dataset"));
        eiosDataset.addProperty(DC_11.creator, model.createLiteral(
                "The Epidemic Intelligence from Open Sources (EIOS) initiative is a unique collaboration between various public health stakeholders around the globe. It brings together new and existing initiatives, networks and systems to create a unified all-hazards, One Health approach to early detection, verification, assessment and communication of public health threats using publicly available information. The EIOS community of practice is supported by an evolving EIOS system, which not only connects other systems and actors – including ProMED, HealthMap and the Global Public Health Intelligence Network (GPHIN) – but also promotes and catalyses new and innovative collaborative development. ",
                "en"));
        eiosDataset.addProperty(DC_11.description, model.createLiteral("", "en"));
        // eiosDataset.addProperty(DC_11.publisher
        eiosDataset.addProperty(DC_11.source, RDFUtils.WHO_URL);
        eiosDataset.addProperty(DC_11.title,
                model.createLiteral("Epidemic Intelligence from Open Sources (EIOS)", "en"));
        eiosDataset.addProperty(model.createProperty(RDFUtils.VOID_NS, "uriSpace"), RDFUtils.WHO_URL);
        eiosDataset.addProperty(model.createProperty(FOAF.HOMEPAGE.stringValue()),
                "https://www.who.int/initiatives/eios");
        eiosDataset.addProperty(vocabulary, ""); // TODO
        // curie.csv

        //        :DBpedia_Geonames a void:Linkset;
        //        void:target :DBpedia;
        //        void:target :Geonames;

        //        <http://example.com/about>
        //            dcterms:contributor         [ a           foaf:Person ;
        //                                          rdfs:label  "Name"@en ;
        //                                          foaf:mbox   "mailto:john@example.com"
        //                                        ] ;
        //            dcterms:created             "2022-12-21"^^<http://www.w3.org/2001/XMLSchema#date> ;
        //            dcterms:creator             [ a           foaf:Person ;
        //                                          rdfs:label  "Name"@en ;
        //                                          foaf:mbox   "mailto:john@example.com"
        //                                        ] ;
        //            dcterms:date                "2022-12-21"^^<http://www.w3.org/2001/XMLSchema#date> ;
        //            dcterms:description         "A textual description of the dataset. If several descriptions are provided they should correspond to translations of the same description rather than different descriptions."@en ;
        //            dcterms:issued              "2022-12-21"^^<http://www.w3.org/2001/XMLSchema#date> ;
        //            dcterms:modified            "2022-12-21"^^<http://www.w3.org/2001/XMLSchema#date> ;
        //            dcterms:publisher           [ a           foaf:Organization ;
        //                                          rdfs:label  "Name"@en ;
        //                                          foaf:mbox   "mailto:john@example.com"
        //                                        ] ;
        //            dcterms:source              <https://example.org> ;
        //            dcterms:title               "The name of the dataset."@en ;
        //            void:classes                5 ;
        //            void:dataDump               <https://example.org/dump> ;
        //            void:distinctObjects        50 ;
        //            void:distinctSubjects       50 ;
        //            void:documents              10 ;
        //            void:entities               100 ;
        //            void:exampleResource        <https://example.org/example> ;
        //            void:feature                <http://www.w3.org/ns/formats/Turtle> ;
        //            void:feature                [ a             void:TechnicalFeature ;
        //                                          rdfs:comment  "A longer description of the feature."@en ;
        //                                          rdfs:label    "A short and descriptive label of the feature, e.g. \"HTTP ETag support\"."@en ;
        //                                          rdfs:seeAlso  <https://example.org/see-also>
        //                                        ] ;
        //            void:openSearchDescription  <An OpenSearch description document for a free-text search service over a void:Dataset.> ;
        //            void:properties             125 ;
        //            void:rootResource           <https://example.org/root> ;
        //            void:sparqlEndpoint         <https://example.org/query> ;
        //            void:triples                500 ;
        //            void:uriLookupEndpoint      <https://example.org/data> ;
        //            void:uriRegexPattern        "^http://dbpedia\\\\.org/(.+)\\\\.ttl$" ;
        //            void:uriSpace               "http://dbpedia.org/resource/" ;
        //            void:vocabulary             dcterms: ;
        //            foaf:homepage               <https://example.org> ;
        //            foaf:page                   <https://example.org/more_information> .

        return model;
    }
}
