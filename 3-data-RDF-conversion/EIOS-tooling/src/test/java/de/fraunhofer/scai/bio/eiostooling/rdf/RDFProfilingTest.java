package de.fraunhofer.scai.bio.eiostooling.rdf;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.junit.Ignore;
import org.junit.Test;

public class RDFProfilingTest {
    
    final static String dataSetUri = "https://test";
    
    @Test @Ignore
    public void profileModelTest() {      
        
        Model model = ModelFactory.createDefaultModel();
        
        model.read("./src/test/resources/chunk_1.ttl");
        Model profile = RDFProfiling.profileModel(model, dataSetUri);
        
        Resource dataset = model.createResource(dataSetUri);
        
        assertEquals(24, profile.size());
        assertTrue(profile.containsResource(dataset));
        assertTrue(profile.contains(dataset, model.createProperty("http://rdfs.org/ns/void#triples")));  
    }

    @Test @Ignore
    public void profileRemoteModelTest() {      

        Model model = ModelFactory.createDefaultModel();
        Model profile = RDFProfiling.profileRemoteModel("https://dbpedia.org/sparql", dataSetUri);

        Resource dataset = model.createResource(dataSetUri);
        
        assertEquals(18, profile.size());
        assertTrue(profile.containsResource(model.createResource(dataSetUri)));
        assertTrue(profile.contains(dataset, model.createProperty("http://rdfs.org/ns/void#triples")));
    }
    
    @Test @Ignore
    public void enrichVoidFilelTest() throws IOException {      

        File input = new File("./src/test/resources/void_wiktionary-dbpedia-org.ttl");
        
        Model iModel = RDFUtils.readModelFromFile(input);
        Model oModel = RDFProfiling.enrichVoidFile(input);

        RDFUtils.logStatements( oModel );
        assertTrue(iModel.size() < oModel.size());
    }

    @Test @Ignore
    public void enrichVoidFromDirTest() throws IOException {      

        RDFProfiling.enrichVoidFromDir("./src/main/resources/output/void");
    }

}
