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
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import de.fraunhofer.scai.bio.Document;
import de.fraunhofer.scai.bio.eiostooling.rdf.RDFUtils;
import intl.who.eios.item.EiosQueryResult;
import lombok.extern.slf4j.Slf4j;

/**
 * main method and argument parsing
 * 
 * @author Marc Jacobs
 *
 */
@Slf4j public class App {

    private static final String OUTDIR = "output";

    /**
     * main for commandline parsing
     * 
     * @param args
     */
    public static void main(String[] args) {

        Logger rootLogger = 
                (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);

        rootLogger.setLevel(Level.INFO);

        String inputPath = null; 
        String outputPath = null; 
        String input = null;
        String output = null;
        String mappings = null;
        int chunksize = 1000;
        int limit = 1000;

        EiosQueryResult result = null;

        List<Document> documents = null;

        Options options = new Options();
        options.addOption("ip", "inputpath", true, "filepath to inputs");
        options.addOption("op", "outputpath", true, "filepath to outputs");
        options.addOption("i", "input", true, "input filename");
        options.addOption("o", "output", true, "output file name");
        options.addOption("m", "mappings", true, "mappings input file name");
        options.addOption("l", "limit", true, "limit size of EIOS query");        
        options.addOption("?", "help", false, "showing argumemts");
        options.addOption("r", "RDF", true, "RDF output format");
        options.addOption("s", OUTDIR, false, "export SCAIView documents");
        options.addOption("c", "chunksize", true, "how many documents in one chunk");
        options.addOption("sk", "skos", true, "write skos mappings");
        options.addOption("q", "query", true, "category to be searched");
        options.addOption("zp", "zipProcess", true, "input zip file");
        options.addOption("idx", "indexes", true, "indexes to be queried as a list");
        options.addOption("li", "listindexes", false, "list all indexes");
        
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);

            if(cmd.hasOption("listindexes")) {
                log.info(AppIOHelper.listIndexes());
                System.exit(0);
            }

            if(cmd.hasOption("help")) {
                printHelpAndExit(options);
            }

            if(cmd.hasOption("inputpath")) {
                inputPath = cmd.getOptionValue("inputpath");
            } else {
                inputPath = "./src/main/resources/input";
            }

            if(cmd.hasOption("outputpath")) {
                outputPath = cmd.getOptionValue("outputpath");
            } else {
                outputPath = "./src/main/resources/output";
            }

            if(cmd.hasOption("zp")) {
                AppIOHelper.processZipFile(cmd.getOptionValue("zp"), outputPath, cmd.hasOption("s"), cmd.hasOption("r"), cmd.getOptionValue("r"), chunksize);
                System.exit(0);
            }

            if(cmd.hasOption("input")) {
                input = cmd.getOptionValue("input");
                result = AppIOHelper.loadInput(inputPath, input);
            }

            if(cmd.hasOption("output")) {
                output = cmd.getOptionValue("output");
            } else {
                output = outputPath + File.separator + input;
            }

            if(cmd.hasOption("mappings")) {
                mappings = cmd.getOptionValue("mappings");
            }

            if(cmd.hasOption("chunksize")) {
                chunksize = Integer.parseInt(cmd.getOptionValue("chunksize"));
            }

            if(cmd.hasOption("skos")) {

                RDFUtils.createMappings(
                        RDFUtils.parsingPrefixes(),
                        "./src/main/resources/curie.csv",
                        cmd.getOptionValue("skos")
                        );

                System.exit(0);
            }

            if(cmd.hasOption("limit")) {
                limit = Integer.parseInt(cmd.getOptionValue("limit"));
            }

            if(cmd.hasOption("query")) {
                String query = cmd.getOptionValue("query");
                
                if(!cmd.hasOption("indexes")) {
                    log.error(" >> no index to be queried provided.");
                    printHelpAndExit(options);
                }
                    
                String idxs = cmd.getOptionValue("indexes");

                if(idxs == null || idxs.isEmpty()) {
                    log.error(" >> no index to be queried provided.");
                    printHelpAndExit(options);                    
                }
                
                for(String idx : idxs.split(",")) {
                    result = AppIOHelper.queryEIOS(idx, 0, chunksize, limit, query, outputPath, cmd.hasOption("r"), cmd.hasOption("s"), cmd.getOptionValue("r"));
                }
                
                System.exit(0);
            }

        } catch (ParseException e2) {
            log.error(e2.getLocalizedMessage());
            log.debug(e2.getLocalizedMessage(), e2);
            printHelpAndExit(options);
        }

        if(mappings == null && result == null) {
            log.error("No input provided (either EIOS query object or SCAIView document)");
            System.exit(1);
        }

        log.info(" >> starting EIOS Tooling");

                        
        if(result != null) {

            if(cmd.hasOption("r")) {
                AppIOHelper.exportRDF(result, outputPath, "rdf_"+output, cmd.getOptionValue("r"));
            }

            if(cmd.hasOption("s")) {
                documents = AppIOHelper.exportSCAIViewDocument(result, outputPath, "scaiview_"+output);            
            }
        }

        if(mappings != null) {
            AppIOHelper.extractRDFMappingsFromFile(outputPath, inputPath + File.separator + mappings, RDFUtils.parsingPrefixes());
            AppIOHelper.extractMentioningsFromFile(outputPath, inputPath + File.separator + mappings, RDFUtils.parsingPrefixes());
        }

        if(documents != null && !documents.isEmpty()) {
            AppIOHelper.extractRDFMappingsFromList(documents, outputPath, FilenameUtils.getBaseName(input), chunksize);
            AppIOHelper.extractMentioningsFromList(documents, outputPath, FilenameUtils.getBaseName(input), chunksize);
        }

        log.info(" >> exit.");
    }
    
    /**
     * command line help
     * @param options
     */
    private static void printHelpAndExit(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("EIOS Tooling", options);
        System.exit(1);
    }

}
