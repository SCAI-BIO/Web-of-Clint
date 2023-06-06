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
package de.fraunhofer.scai.bio.eiostooling;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.fraunhofer.scai.bio.Document;
import de.fraunhofer.scai.bio.eiostooling.doctypesystem.DocumentBuilder;
import de.fraunhofer.scai.bio.eiostooling.doctypesystem.DocumentUtils;
import de.fraunhofer.scai.bio.eiostooling.rdf.AnnotationRDFBuilder;
import de.fraunhofer.scai.bio.eiostooling.rdf.ItemRDFBuilder;
import de.fraunhofer.scai.bio.eiostooling.rdf.MentioningBuilder;
import de.fraunhofer.scai.bio.eiostooling.rdf.RDFUtils;
import intl.who.eios.item.EiosQueryResult;
import intl.who.eios.item.Hit;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * subroutines for the app
 * 
 * @author Marc Jacobs
 *
 */
@Slf4j public class AppIOHelper {

    /**
     * 
     * go through all subfolder, extract query json, convert rdf, mappings and scaiview documents and store in similar directory structure 
     * @param fileZip input file
     * @param outputPath output directory
     * @param toScaiview convert to scaiview documents
     * @param toRdf convert to RDF output
     * @param rdfFormat rdf format - eg TTL
     * @param chunksize how many documents in a ZIP chunk
     */
    public static void processZipFile(String fileZip, String outputPath, boolean toScaiview, boolean toRdf, String rdfFormat, int chunksize) {

        EiosQueryResult result;
        try {
            File destDir = new File(outputPath + File.separator + FilenameUtils.getBaseName(fileZip));
            String zipName = FilenameUtils.getBaseName(fileZip);

            byte[] buffer = new byte[1024];
            @SuppressWarnings("resource")
            ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
            ZipEntry zipEntry = zis.getNextEntry();

            while (zipEntry != null) {
                File newFile = newFile(destDir, zipEntry);
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    // fix for Windows-created archives
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }

                    // write file content
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();

                    result = loadInput(newFile);

                    String output = FilenameUtils.getBaseName(newFile.getName());

                    if(toRdf) {
                        File rdfFile = new File(newFile.getParent().replace(zipName, "rdf_"+zipName));
                        rdfFile.mkdirs();

                        exportRDF(result, rdfFile.getCanonicalPath(), output, rdfFormat);
                    }

                    if(toScaiview) {
                        File svFile = new File(newFile.getParent().replace(zipName, "scaiview_"+zipName));
                        svFile.mkdirs();

                        List<Document> documents = exportSCAIViewDocument(result, svFile.getCanonicalPath(), output);            

                        if(documents != null && !documents.isEmpty()) {
                            File mapFile = new File(newFile.getParent().replace(zipName, "mapping_"+zipName));
                            mapFile.mkdirs();
                            extractRDFMappingsFromList(documents, mapFile.getCanonicalPath(), output, chunksize);

                            File mentionFile = new File(newFile.getParent().replace(zipName, "mentioning_"+zipName));
                            mentionFile.mkdirs();
                            extractMentioningsFromList(documents, mentionFile.getCanonicalPath(), output, chunksize);
                        }
                    }

                    newFile.delete();
                }

                zipEntry = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            log.debug(e.getLocalizedMessage(), e);
        }

        return;
    }

    /**
     * extract RDF mappings from document file
     * 
     * @param basePath
     * @param mappings
     * @param prefixes 
     */
    public static void extractRDFMappingsFromFile(String outPath, String mappings, Map<String, String> prefixes) {
        Document doc = null;
        try {
            doc = DocumentUtils.read(mappings);
            AnnotationRDFBuilder ab = new AnnotationRDFBuilder(doc, prefixes);

            RDFUtils.writeModelToFile(ab.getAnnotationsModel(), outPath, "mappings_"+FilenameUtils.getBaseName(mappings), "TTL");
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            log.debug(e.getLocalizedMessage(), e);
        }
    }

    /**
     * extract RDF mentioning from document file
     * 
     * @param doc <code>Document</code>
     * @param prefixes RDF prefix map
     * @param outPath where to store
     * @param filename under which name
     */
    public static void extractMentioningsFromDocument(Document doc, Map<String, String> prefixes, String outPath, String filename) {
        try {
            MentioningBuilder mb = new MentioningBuilder(doc, prefixes, false);
            RDFUtils.writeModelToFile(mb.getMentionsModel(), outPath, "mentionings_"+filename, "TTL");
            
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            log.debug(e.getLocalizedMessage(), e);
        }
    }
    
    /**
     * extract RDF mappings from document file
     * 
     * @param basePath
     * @param filename
     * @param prefixes 
     */
    public static void extractMentioningsFromFile(String outPath, String filename, Map<String, String> prefixes) {
        try {            
            extractMentioningsFromDocument(DocumentUtils.read(filename), 
                    prefixes, outPath, 
                    FilenameUtils.getBaseName(filename)
                    );

        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            log.debug(e.getLocalizedMessage(), e);
        }
    }

    /**
     * @param result
     * @param path
     * @param output
     */
    public static List<Document> exportSCAIViewDocument(EiosQueryResult result, String path, String output) {
        List<Document> documents = new ArrayList<Document>();

        Map<String, String> mappings = DocumentBuilder.parsingMappings();

        byte[] data;
        ZipEntry e;                   
        File f = new File(path + File.separator +output + ".zip");
        ZipOutputStream out;
        int i = 0;
        try {
            out = new ZipOutputStream(new FileOutputStream(f));

            int j=0;
            for(Hit eiositem : result.getHits().getHits()) {
                DocumentBuilder db = new DocumentBuilder(eiositem.getSource(), ++i, mappings);

                try {
                    if(db.getDocumentOrig() != null) {
                        e = new ZipEntry(eiositem.getId() + "_" + eiositem.getSource().getLanguageCode() + ".json");
                        out.putNextEntry(e);
                        data = db.writeToJSON(db.getDocumentOrig()).getBytes();
                        out.write(data, 0, data.length);
                        ++j;
                        documents.add(db.getDocumentOrig());
                    }                    

                    if(db.getDocumentTranslated() != null) {
                        e = new ZipEntry(eiositem.getId() + "_en.json");
                        out.putNextEntry(e);
                        data = db.writeToJSON(db.getDocumentTranslated()).getBytes();
                        out.write(data, 0, data.length);   
                        ++j;
                        documents.add(db.getDocumentTranslated());
                    }

                    out.closeEntry();

                } catch (IOException ex) {
                    log.error(ex.getLocalizedMessage());
                    log.debug(ex.getLocalizedMessage(), ex);
                }            
            }
            out.close();
            log.info(" >> created {} documents in {} ", j, f.getAbsolutePath());

        } catch (Exception e1) {
            log.error(e1.getLocalizedMessage());
            log.debug(e1.getLocalizedMessage(), e1);
        }

        return documents;
    }


    /**
     * helper for zip fileprocessing
     * 
     * @param destinationDir
     * @param zipEntry
     * @return
     * @throws IOException
     */
    private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }

    /**
     * @param documents
     * @param basePath 
     * @param output 
     */
    public static void extractRDFMappingsFromList(List<Document> documents, String basePath, String output, int chunksize) {

        byte[] data;
        ZipEntry e;                   
        File f = new File(basePath + File.separator +"mappings_"+output + ".zip");
        ZipOutputStream out;
        try {
            out = new ZipOutputStream(new FileOutputStream(f));

            int i=0;
            int j=0;
            Model mappingModel = ModelFactory.createDefaultModel();

            Map<String, String> prefixes = RDFUtils.parsingPrefixes();

            for(Document doc : documents) {
                try {
                    AnnotationRDFBuilder ab = new AnnotationRDFBuilder(doc, prefixes);
                    mappingModel.add(ab.getAnnotationsModel());

                    ++i;

                    if(i%chunksize==0  || i==documents.size()) {
                        String id;

                        if(chunksize == 1) {
                            id = "mappings_"+ doc.getDocumentElement().getMetaElement().getConcept().getIdentifier().getText() + ".ttl";
                        } else {
                            id = "chunk_" + ++j + ".ttl";
                        }
                        e = new ZipEntry(id);
                        out.putNextEntry(e);
                        data = RDFUtils.writeModelToString(mappingModel, "TTL").getBytes();                    
                        out.write(data, 0, data.length);
                        out.closeEntry(); 

                        mappingModel.removeAll();
                    }

                } catch (IOException ex) {
                    log.error(ex.getLocalizedMessage());
                    log.debug(ex.getLocalizedMessage(), ex);
                }            
            }

            out.close();
            log.info(" >> created mappings in {} ", f.getAbsolutePath());

        } catch (Exception e1) {
            log.error(e1.getLocalizedMessage());
            log.debug(e1.getLocalizedMessage(), e1);
        }
    }

    /**
     * @param documents
     * @param basePath 
     * @param output 
     */
    public static void extractMentioningsFromList(List<Document> documents, String basePath, String output, int chunksize) {

        byte[] data;
        ZipEntry e;                   
        File f = new File(basePath + File.separator +"mentionings_"+output + ".zip");
        ZipOutputStream out;
        try {
            out = new ZipOutputStream(new FileOutputStream(f));

            int i=0;
            int j=0;
            Model mentioningModel = ModelFactory.createDefaultModel();

            Map<String, String> prefixes = RDFUtils.parsingPrefixes();

            for(Document doc : documents) {
                try {
                    MentioningBuilder mb = new MentioningBuilder(doc, prefixes, false);
                    mentioningModel.add(mb.getMentionsModel());

                    ++i;

                    if(i%chunksize==0  || i==documents.size()) {
                        String id;

                        if(chunksize == 1) {
                            id = "mentionings_"+ doc.getDocumentElement().getMetaElement().getConcept().getIdentifier().getText() + ".ttl";
                        } else {
                            id = "chunk_" + ++j + ".ttl";
                        }
                        e = new ZipEntry(id);
                        out.putNextEntry(e);
                        data = RDFUtils.writeModelToString(mentioningModel, "TTL").getBytes();                    
                        out.write(data, 0, data.length);
                        out.closeEntry(); 

                        mentioningModel.removeAll();
                    }

                } catch (IOException ex) {
                    log.error(ex.getLocalizedMessage());
                    log.debug(ex.getLocalizedMessage(), ex);
                }            
            }

            out.close();
            log.info(" >> created mentionings in {} ", f.getAbsolutePath());

        } catch (Exception e1) {
            log.error(e1.getLocalizedMessage());
            log.debug(e1.getLocalizedMessage(), e1);
        }
    }

    /**
     * json parsing from file 
     * 
     * @param inputJson
     * @return
     */
    private static EiosQueryResult loadInput(File inputJson) {

        log.info(">> loading {}",inputJson.getAbsolutePath());

        ObjectMapper mapper = null;
        EiosQueryResult result = null;
        try {
            mapper = new ObjectMapper();
            result = mapper.readValue(inputJson, EiosQueryResult.class);

        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
            log.debug(ex.getLocalizedMessage(), ex);
            System.exit(2);
        }

        log.info(" >> got {} documents", result.getHits().getHits().size());
        return result;
    }


    /**
     * @param basePath
     * @param input
     * @return
     */
    public static EiosQueryResult loadInput(String basePath, String input) {
        File inputJson = new File(basePath + File.separator + input);

        return loadInput(inputJson);
    }

    /**
     * query elastic search for a list of indices
     * 
     * @return list <code>String</code>
     */
    public static String listIndexes() {
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://portal.who.int/elasticsearch/_cat/indices").newBuilder();
        urlBuilder.addQueryParameter("pretty", "true");

        try {
            Request request = new Request.Builder()
                    .header("Authorization", "Basic YmFieV9zaGFyazohRWw0c3QxY0BlMTA1")
                    .url(urlBuilder.build().toString())
                    .get()
                    .build();
            log.info(" >> query EIOS indices..");
            Response response = client.newCall(request).execute();
            log.info(" >> EIOS: {} - {}, retrieving data...", response.code(), response.message());

            if(response.code() == 200) {
                log.info(" >> ready.");
                return( " >> available indices: \n" + new String(response.body().bytes()) );
            }
        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
            log.info(ex.getLocalizedMessage(), ex);
            System.exit(2);
        }

        return "";
    }

    /**
     * query elastic search for a list of items 
     * uses paginated calls to get all data
     * 
     * @param idx <code>String</code> search index of elastic
     * @param from <code>Integer</code> offset
     * @param chunk <code>Integer</code> number of results per query
     * @param limit <code>Integer</code> total number of results
     * @param queryFile <code>String</String> file name which contains the query
     * @param outPath <code>String</String> path where to store the results as JSON
     * @return <code>EiosQueryResult</code> last chunk of results
     */
    public static EiosQueryResult queryEIOS(String idx, Integer from, Integer chunk, Integer limit, String queryFile, String outPath) {
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(300, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS)
                .build();

        EiosQueryResult result = null;

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://portal.who.int/elasticsearch/" + idx + "/_search").newBuilder();
        urlBuilder.addQueryParameter("size", chunk.toString());        
        urlBuilder.addQueryParameter("pretty", "true");

        RequestBody body;
        try {

            int count = 0;
            String last = "{\n";
            String q = FileUtils.readFileToString(new File(queryFile), "UTF-8");
            do {
                String q1 = q.replaceFirst("\"search_after\": .*", "");
                String q2 = q1.replaceFirst("\\{", last);
                body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), 
                        q2
                        );

                Request request = new Request.Builder()
                        .header("Authorization", "Basic YmFieV9zaGFyazohRWw0c3QxY0BlMTA1")
                        .url(urlBuilder.build().toString())
                        .post(body)
                        .build();
                log.info(" >> query EIOS chunk {} with timeout {}..", chunk, "300 seconds");
                Response response = client.newCall(request).execute();
                log.info(" >> EIOS: {} - {}, retrieving data...", response.code(), response.message());

                if(response.code() == 200) {
                    ObjectMapper mapper = null;
                    mapper = new ObjectMapper();
                    result = mapper.readValue(response.body().bytes(), EiosQueryResult.class);
                    List<Hit> hits = result.getHits().getHits();

                    count += hits.size();
                    if(count >= limit || hits.size() <= 0) break;
                    log.info(" >> got {} hits of {}",  String.format("%,d", count), String.format("%,d", result.getHits().getTotal().getValue()) );

                    // TODO truncate if larger than limit
                    if(outPath != null) {

                        String basename = FilenameUtils.getBaseName(queryFile);
                        String fname = String.format("%s/%s/%s/chunk_%08d.json" , outPath, basename, idx, count);

                        FileUtils.writeStringToFile(
                                new File(fname), 
                                mapper.writerWithDefaultPrettyPrinter().writeValueAsString( result ), 
                                Charset.forName("UTF-8"));

                        log.info(" >> written {} ", fname);
                    }

                    String hit = hits.get(hits.size()-1).getAdditionalProperties().get("sort").toString();
                    last =   "{\n" + "\"search_after\": " + hit + ",";
                    log.info(" >> last {}", hit);
                }

            } while (true);


        } catch (Exception ex) {
            log.error(ex.getLocalizedMessage());
            log.debug(ex.getLocalizedMessage(), ex);
            System.exit(2);
        }

        log.info(" >> ready.");

        return result;
    }

    /**
     * @param result
     * @param basePath
     * @param output
     * @param format
     */
    public static void exportRDF(EiosQueryResult result, String basePath, String output, String format) {
        if(result != null) {

            byte[] data;
            ZipEntry e;                   
            File f = new File(basePath + File.separator +output + ".zip");
            ZipOutputStream out;
            try {
                out = new ZipOutputStream(new FileOutputStream(f));
                ItemRDFBuilder rdf = new ItemRDFBuilder(result);

                for(Entry<String, Model> entry : rdf.getDocumentsModel().entrySet()) {
                    try {
                        e = new ZipEntry(entry.getKey() + ".ttl");
                        out.putNextEntry(e);
                        data = RDFUtils.writeModelToString(entry.getValue(), "TTL").getBytes();                    
                        out.write(data, 0, data.length);
                        out.closeEntry();

                    } catch (IOException ex) {
                        log.error(ex.getLocalizedMessage());
                        log.debug(ex.getLocalizedMessage(), ex);
                    }            
                }

                out.close();
                log.info(" >> created rdf in {} ", f.getAbsolutePath());

            } catch (Exception e1) {
                log.error(e1.getLocalizedMessage());
                log.info(e1.getLocalizedMessage(), e1);
            }
        }
    }


}
