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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.shared.NoWriterForLangException;
import org.apache.jena.ttl.turtle.TurtleReader;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.DC_11;
import org.apache.jena.vocabulary.OA;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.SKOS;
import org.eclipse.rdf4j.model.vocabulary.FOAF;
import org.eclipse.rdf4j.model.vocabulary.PROV;
import org.springframework.core.io.ClassPathResource;

import de.fraunhofer.scai.bio.eiostooling.doctypesystem.DocumentBuilder;
import de.fraunhofer.scai.bio.types.text.doc.meta.Annotation;
import lombok.extern.slf4j.Slf4j;


/**
 * some utilities for working with 
 * @see org.apache.jena.rdf.model.Model
 *  
 * @author Marc Jacobs
 *
 */
@Slf4j public class RDFUtils {

    public static final String WHO_URL = "https://int.who/";
    public static final String SCAI_URL = "https://www.scai.fraunhofer.de/";
    public static final String EIOS = "https://www.scai.fraunhofer.de/EiosTooling/";
    public static final String DOCUMENT = "Document_";
    public static final String TEXT_ELEMENT = "TextElement_";
    public static final String DAY = "Day_";
    public static final String VOID_NS = "http://rdfs.org/ns/void#";
    public static final String LANGUAGE_CODE = "LanguageCode:";

    public static String writeModelToString(Model model, String format) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        writeModelToStream(model, baos, format);
        baos.close();

        return baos.toString();
    }

    public static void writeModelToStream(Model model, OutputStream fout, String format) throws IOException {
        model.write(fout, format);
        fout.close();
    }

    public static Model readModelFromFile(File file) throws IOException  {
        TurtleReader turtleReader = new TurtleReader();
        Model model = ModelFactory.createDefaultModel();
        turtleReader.read(model, new FileReader(file), null);

        return model;
    }

    /**
     * @param name
     * @param format
     * @param path 
     * @return 
     * @throws IOException
     */
    public static boolean writeModelToFile(Model model, String path, String name, String format) throws IOException {

        if(model != null && model.size() > 0) {
            String suffix = null;

            try {
                if(format.equals("N3")) { suffix = ".n3"; } 
                else if(format.equals("RDF/XML")) { suffix = ".rdf"; } 
                else if(format.equals("N-TRIPLE")) { suffix = ".nt"; }
                else if(format.equals("TURTLE")) { suffix = ".ttl"; }
                else if(format.equals("TTL")) { suffix = ".ttl"; }
                else if(format.equals("gephi")) {
                    RDFUtils.writeGephiTable(model, name);
                }

                if(suffix != null) {
                    String fname = path + File.separator + name+suffix;
                    FileOutputStream fout=new FileOutputStream(fname);
                    RDFUtils.writeModelToStream(model, fout, format);
                    log.info(" >> written {}", fname);

                    return true;
                } else {
                    log.error("supported formats: N3, RDF/XML, N-TRIPLE, TURTLE, TTL, gephi");

                }
            } catch(NoWriterForLangException ex) {
                log.error("supported formats: N3, RDF/XML, N-TRIPLE, TURTLE, TTL, gephi");
            }
        } else {
            log.warn("empty model - abort.");
        }

        return false;
    }

    /**
     * @param name
     */
    public static void writeGephiTable(Model model, String name) {
        FileWriter fw = null;
        List<Resource> nodes = new ArrayList<Resource>();
        List<Resource> errors = new ArrayList<Resource>();

        try {
            fw = new FileWriter( "./" + name + "_node_table.csv" );
            fw.write("Id;Label;Class");
            fw.append( System.getProperty("line.separator") );

            int i = 0;

            Query query = QueryFactory.create(
                    "SELECT ?x ?c ?l " 
                            + "WHERE { "
                            + "?x  <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?c . "
                            + "?x <" + RDFS.label +"> ?l ." 
                            + "}" 
                    );

            try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
                ResultSet results = qexec.execSelect() ;
                for ( ; results.hasNext() ; ) {
                    QuerySolution soln = results.nextSolution() ;

                    Resource node = soln.getResource("x");

                    log.info("{} - {} - {}", soln.getLiteral("l"), node, soln.getResource("c"));

                    if(!nodes.contains(node)) {     
                        nodes.add(node);
                        fw.write( ""+ i++ );
                        fw.write( ";" );
                        fw.write( "\"" + soln.getLiteral("l") + "\"" );
                        fw.write( ";" );
                        fw.write( "\"" + soln.getResource("c") + "\"" );
                        fw.append( System.getProperty("line.separator") );
                    }
                }
            } catch (Exception ex) {
                log.error("Couldn't create node" );
                log.debug(ex.getLocalizedMessage(), ex);

            } finally {
                if ( fw != null )
                    try { fw.close(); } catch ( IOException e ) { e.printStackTrace(); }
            }

        } catch ( IOException e ) {
            log.error("Couldn't create file" );
            log.debug(e.getLocalizedMessage(), e);
        }

        try {
            fw = new FileWriter( "./" + name + "_edge_table.csv" );
            fw.write("Source;Target;Label;Class");
            fw.append( System.getProperty("line.separator") );

            StmtIterator  iter = model.listStatements();
            while(iter.hasNext()) {
                Statement stmt = iter.nextStatement();

                Resource from = stmt.getSubject();

                if(stmt.getObject().isResource()) {
                    Resource to = stmt.getObject().asResource();

                    if(nodes.lastIndexOf(to) > 0) {
                        if(nodes.lastIndexOf(from) > 0) {

                            fw.write("" + nodes.lastIndexOf(from));
                            fw.write(";");
                            fw.write("" + nodes.lastIndexOf(to));
                            fw.write(";");      
                            fw.write(stmt.getPredicate().getLocalName());
                            fw.write(";");
                            fw.write(stmt.getPredicate().getURI());
                            fw.append( System.getProperty("line.separator") );
                        } else {
                            if(!errors.contains(from)) {
                                errors.add(from); 
                            }
                        }
                    } else {
                        if(!errors.contains(to)) {
                            errors.add(to); 
                        }
                    }
                }
            }

            log.warn(" >> errors / problems:");
            for(Resource res : errors) {
                log.warn("{} is not a node", res);
            }

        } catch ( IOException e ) {
            log.error("Couldn't create file" );
            log.debug(e.getLocalizedMessage(), e);

        } finally {
            if ( fw != null )
                try { fw.close(); } catch ( IOException e ) { e.printStackTrace(); }
        }
    }

    /**
     * @param url
     * @return
     * @throws MalformedURLException
     * @throws URISyntaxException
     */
    public static boolean isValidURL(String url) throws MalformedURLException, URISyntaxException {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    /**
     * go through the mappings and write to skos scheme
     * 
     * @param prefixes 
     * @param oufile 
     * @param infile 
     * @param delimiter 
     * 
     */
    public static void createMappings(Map<String, String> prefixes, String oufile, String infile, int from, int to, int total, char delimiter) {
        try {
            log.info(" >>> parsing EIOS mappings from {}...", infile);

            InputStream in = new FileInputStream(new File(infile));  //ClassPathResource("new_eios_mappings.csv").getInputStream();
            Model model = ModelFactory.createDefaultModel();
            Property exact = model.createProperty("http://www.w3.org/2004/02/skos/core#exactMatch");

            RDFUtils.addNSPrefixes(model, RDFUtils.parsingPrefixes());

            try {
                CSVParser parser = CSVParser.parse(in, Charset.forName("UTF-8"), CSVFormat.DEFAULT.withAllowMissingColumnNames().withDelimiter(delimiter));

                for(CSVRecord record : parser.getRecords()) { 
                    if(record.getRecordNumber() > 1l && record.isConsistent() && record.size() == total) {
                        if(!record.get(to).isEmpty() && !record.get(from).isEmpty()) {
//                            Resource anot = model.createResource("https://int.who/EIOS_category/" + record.get(0).replaceAll("\\s", "_"));
                            Resource anot = model.createResource(record.get(from).trim());
                            anot.addProperty(exact, model.createResource(record.get(to).trim())); 
                        } else {
                            log.error("empty record: {}", record.toString());
                        }

                    } else {
                        log.error("invalid record: {}", record.toString());
                    }

                }
            } catch (IOException e) {
                log.error(e.getLocalizedMessage());
                log.debug(e.getLocalizedMessage(), e);
            }


            // category:TyphoidFever -> "Typhoid fever,typhoid fever,DOID_13258,http://purl.obolibrary.org/obo/DOID_13258"


            //                                    a                 who:Category ;
            //                                    rdfs:label        "Coronavirus"@en , "coronavirus"@en ;
            //                                    dc11:identifier   "category:CoronavirusInfection" ;
            //                                    EIOS:mentionedIn  who:TextElement_66ef5400-f9b1-3efd-a024-c4753c68fea8 , who:TextElement_17a9b981-1cde-36b7-a1be-d4e6dca2ff34 , who:TextElement_f8a27977-8f07-3ca6-bc43-d4a452b5ea3b .
            //
            //                                    @prefix category: <https://int.who/EIOS_category/>
            //
            //                                    category:Cyclospora  a    who:Category ;


            RDFUtils.writeModelToFile(model, new File(infile).getParent() , FileNameUtils.getBaseName(infile), "TTL");
            in.close();

        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            log.debug(e.getLocalizedMessage(), e);
        }

        log.info(" >> done ");
    }


    /**
     * find longest prefix that matches and return the key to it
     * @param prefixes
     * @param uri
     * @return
     */
    private static String findUri(Map<String, String> prefixes, String uri) {

        String prefix = null;
        int maxUri = 0;

        for(String key : prefixes.keySet()) {
            if(uri.startsWith(prefixes.get(key))
                    && prefixes.get(key).length() > maxUri) {
                prefix = key;
            }
        }

        if(prefix == null) {
            log.warn(" >> Couldn'find prefix for {}", uri);
        }
        return prefix;
    }

    /**
     * @return
     */
    public static Map<String, String> parsingPrefixes() {
        Map<String, String> prefixes = new TreeMap<String, String>();

        try {
            log.debug(" >>> parsing prefixes...");

            InputStream in = new ClassPathResource("prefixes.tsv").getInputStream();

            try {
                CSVParser parser = CSVParser.parse(in, Charset.forName("UTF-8"), CSVFormat.TDF);
                parser.forEach(record -> {
                    if(record.isConsistent() && record.size() == 2) {
                        if(!record.get(0).isEmpty() && !record.get(1).isEmpty() && !record.get(0).startsWith("#")) {
                            prefixes.put(record.get(0), record.get(1));
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

            log.debug(" >>> got {} prefixes.", prefixes.size());
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            log.debug(e.getLocalizedMessage(), e);
        }

        return prefixes;
    }

    /**
     * @param model
     * @param prefixes
     */
    public static void addNSPrefixes(Model model, Map<String, String> prefixes) {
        model.setNsPrefix(PROV.PREFIX, PROV.NAMESPACE);
        model.setNsPrefix(FOAF.PREFIX, FOAF.NAMESPACE);
        model.setNsPrefix("dcterms", DCTerms.NS);
        model.setNsPrefix("dc11",DC_11.NS);
        model.setNsPrefix("skos", SKOS.getURI());
        model.setNsPrefix("scai", SCAI_URL);
        model.setNsPrefix("who", WHO_URL);
        model.setNsPrefix("gn", "https://sws.geonames.org/");
        model.setNsPrefix("wgs84_pos", "http://www.w3.org/2003/01/geo/wgs84_pos#");
        model.setNsPrefix("schema", "http://schema.org/");
        model.setNsPrefix("oa", OA.NS);
        model.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        model.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
        model.setNsPrefix("dbpedia","http://dbpedia.org/resource/");

        if(prefixes != null) {
            prefixes.entrySet().forEach(entry -> {
                model.setNsPrefix(entry.getKey(), entry.getValue());
            });
        }

    }

    /**
     * @param dataset
     * @return
     */
    public static Model createProvenance(String dataset) {
        Calendar cal = Calendar. getInstance();
        cal. setTime(new Date(System.currentTimeMillis()));

        Model model = ModelFactory.createDefaultModel();

        Resource eiosItem = model.createResource(dataset);

        Resource scai = model.createResource(SCAI_URL);
        scai.addProperty(RDF.type, model.createResource(PROV.ORGANIZATION.stringValue()));
        scai.addProperty(RDFS.label, model.createLiteral("Fraunhofer Institute of Algorithms and Scientific Computing (SCAI)", "en"));

        Resource eiosTooling = model.createResource("https://www.scai.fraunhofer.de/EiosTooling");
        //eiostooling.addProperty(RDF.type, DCTerms.ProvenanceStatement);
        eiosTooling.addProperty(RDF.type, model.createResource("http://www.w3.org/ns/prov#SoftwareAgent"));
        eiosTooling.addProperty(RDFS.label, model.createLiteral("EIOS Tooling", "en"));
        eiosTooling.addProperty(model.createProperty(PROV.AT_TIME.stringValue()), model.createTypedLiteral(cal));
        eiosTooling.addProperty(model.createProperty(PROV.GENERATED.stringValue()), eiosItem);
        eiosTooling.addProperty(model.createProperty(PROV.NAMESPACE, "actedOnBehalfOf"), scai);       

        Resource eiosETL = model.createResource("https://www.scai.fraunhofer.de/EiosTooling/AnnotationRDFBuilder");
        eiosETL.addProperty(RDF.type, model.createResource(PROV.ACTIVITY.stringValue()));
        eiosETL.addProperty(RDFS.label, model.createLiteral("conversion EIOS to RDF", "en"));
        eiosETL.addProperty(model.createProperty(PROV.USED.stringValue()), eiosItem);
        eiosETL.addProperty(model.createProperty(PROV.WAS_ASSOCIATED_WITH.stringValue()), eiosTooling);

        eiosItem.addProperty(model.createProperty(PROV.WAS_ATTRIBUTED_TO.stringValue()), eiosTooling);
        // TODO        eiosItem.addProperty(model.createProperty(PROV.WAS_DERIVED_FROM.stringValue()), model.createResource(dataset));
        eiosItem.addProperty(model.createProperty(PROV.WAS_GENERATED_BY.stringValue()), eiosETL);

        return model;
    }

    /**
     * create proper type annotations for NEO4j
     * @param model
     * @param anot
     * @param annotation
     */
    public static void createAnnotationClass(Model model, Resource anot, Annotation annotation) {

        log.debug("{}",annotation.getAnnotationType() );
        if(annotation.getAnnotationType().equals("gn")) {
            anot.addProperty(RDF.type, DCTerms.Location);                   
        } else if ( annotation.getAnnotationType().equals("category")) {
            anot.addProperty(RDF.type, model.createResource(RDFUtils.WHO_URL+"Category"));                               
        } else if( annotation.getAnnotationType().equals(DocumentBuilder.PEOPLE)) {
            anot.addProperty(RDF.type, model.createResource(FOAF.PERSON.stringValue()));                                 
        } else if( annotation.getAnnotationType().equals(DocumentBuilder.ORGANIZATIONS)) {
            anot.addProperty(RDF.type, model.createResource(FOAF.ORGANIZATION.stringValue()));                   
        } else {
            anot.addProperty(RDF.type, OWL.Thing);
        }
    }

    /**
     * @param annotation
     * @return
     */
    public static String getCurl(Annotation annotation) {
        return annotation.getAnnotationType()+":"+annotation.getAnnotationText().replaceAll("/$", "");
    }


    /**
     * perform a count query on the model and return <code>long</code>
     * @param model
     * @param sparql - query string; must return a '?count' as long
     * @return
     */
    public static long countModel(Model model, String sparql) {

        Query query = QueryFactory.create(sparql);

        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet results = qexec.execSelect() ;
            QuerySolution result = results.next();
            return result.get("count").asLiteral().getLong();

        } catch (Exception ex) {
            log.error("Couldn't query model" );
            log.info(ex.getLocalizedMessage(), ex);
        }       

        return -1;
    }

    /**
     * perform a SPARQL query and return an array of <code>RDFNode</code>
     * @param model
     * @param sparql - query string
     * @return <code>List<List<RDFNode>></code>
     */
    public static List<List<RDFNode>> queryModel(Model model, String sparql) {

        List<List<RDFNode>> results = new ArrayList<List<RDFNode>>();
        Query query = QueryFactory.create(sparql);

        try (QueryExecution qexec = QueryExecutionFactory.create(query, model)) {
            ResultSet set = qexec.execSelect() ;            
            while(set.hasNext()) {
                QuerySolution result = set.next();

                List<RDFNode> row = new ArrayList<RDFNode>();

                for(String varName : set.getResultVars()) {
                    row.add(result.get(varName));
                }

                results.add(row);
            }

            log.info(results.toString());
            return results;

        } catch (Exception ex) {
            log.error("Couldn't query model" );
            log.info(ex.getLocalizedMessage(), ex);
        }       

        return null;
    }

    /**
     * print all statements to the logger
     * @param model
     */
    public static void logStatements(Model model) {
        if(model == null || model.size() <= 0) {
            log.warn("empty model");
        } else {

            StmtIterator  iter = model.listStatements();
            while(iter.hasNext()) {
                log.info(iter.nextStatement().toString());
            }
        }
    }
}
