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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.fraunhofer.scai.bio.Document;
import de.fraunhofer.scai.bio.types.text.doc.DocumentElement;
import de.fraunhofer.scai.bio.types.text.doc.container.FrontMatter;
import de.fraunhofer.scai.bio.types.text.doc.container.Paragraph;
import de.fraunhofer.scai.bio.types.text.doc.container.Section;
import de.fraunhofer.scai.bio.types.text.doc.container.StructureElement;
import de.fraunhofer.scai.bio.types.text.doc.meta.Annotation;
import de.fraunhofer.scai.bio.types.text.doc.structure.TextElement;

/**
 * some helper functions to work with 
 * @see de.fraunhofer.scai.bio.Document
 * 
 * @author Marc Jacobs
 * @
 *
 */
public class DocumentUtils {
    
    public static Document read(String filename) throws StreamReadException, DatabindException, IOException {
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        return objectMapper.readValue(new File(filename), Document.class);        
    }
    
    public static Map<Annotation, TextElement> extractAnnotations(Document doc) {
        Map<Annotation, TextElement> annotations = new HashMap<Annotation, TextElement>();
        
        DocumentElement de = doc.getDocumentElement();
        
        FrontMatter front = de.getFrontMatter();
        if(front != null) {
            if(front.getTitleText() != null
                    && front.getTitleText().getAnnotations() != null) {
                
                for(Annotation a : front.getTitleText().getAnnotations()) {
                    annotations.put(a, front.getTitleText());
                }
            }
            
            if(front.getDocumentAbstract() != null) {
                List<Section> sections = front.getDocumentAbstract().getAbstractSections();
                
                if(sections != null) {
                    extractSections(annotations, sections);
                }
            }
        }
        
        return annotations;
    }

    private static Map<Annotation, TextElement> extractSections(Map<Annotation, TextElement> annotations, List<Section> sections) {
        
        for(Section sec : sections) {
            if(sec.getParagraphs() != null) {
                extractParagraphs(annotations, sec.getParagraphs());
            }
        }
        
        return annotations;
    }

    private static Map<Annotation, TextElement> extractParagraphs(Map<Annotation, TextElement> annotations, List<Paragraph> paragraphs) {
        
        for(Paragraph par : paragraphs) {
            if(par.getStructureElements() != null) {
                extractStructureElements(annotations, par.getStructureElements());
            }
        }
        
        return annotations;
    }

    private static Map<Annotation, TextElement> extractStructureElements(Map<Annotation, TextElement> annotations, List<StructureElement> elems) {
        
        for(StructureElement elem : elems) {
            if(elem.getTextElement() != null) {
                if(elem.getTextElement().getAnnotations() != null) {
                    for(Annotation a : elem.getTextElement().getAnnotations()) {
                        annotations.put(a, elem.getTextElement());
                    }
                }
                
                if(elem.getSentence() != null 
                        && elem.getSentence().getSentenceText() != null 
                        && elem.getSentence().getSentenceText().getAnnotations() != null) {
                    for(Annotation a : elem.getSentence().getSentenceText().getAnnotations()) {
                        annotations.put(a, elem.getTextElement());
                    }
                }
            }
        }
        
        return annotations;
    }

}
