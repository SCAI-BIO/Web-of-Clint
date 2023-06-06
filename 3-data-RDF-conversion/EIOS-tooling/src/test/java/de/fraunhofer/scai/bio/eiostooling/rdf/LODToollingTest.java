package de.fraunhofer.scai.bio.eiostooling.rdf;

import static org.junit.Assert.assertThrows;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.jena.atlas.logging.Log;
import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.codemodel.JCodeModel;

import de.fraunhofer.scai.bio.lod.data.LodData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LODToollingTest {

    @Test
    public void convertJsonToJavaClassTest() {

        try {
            File file = new File("./src/test/resources/record.json");
            String json = FileUtils.readFileToString(file, "UTF-8");
            LODTooling.convertJsonToJavaClass(json, new File("./src/main/java"), "de.fraunhofer.scai.bio.lod.data", "LodData");
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            log.debug(e.getLocalizedMessage(), e);
        }
    }

    @Test
    public void downloadLODTest() {
        try {
            File file = new File("./src/test/resources/lod-data.json");
            String json = FileUtils.readFileToString(file, "UTF-8");
            
            LODTooling.downloadVoIDfomList(json);
            
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            log.debug(e.getLocalizedMessage(), e);
        }
    }


    @Test
    public void parseJsonViaPathTest() {
        try {
            File file = new File("./src/test/resources/lod-data.json");
            String json = FileUtils.readFileToString(file, "UTF-8");
            
            LODTooling.listEndpoints(json);
            // LODTooling.parseJsonViaPath(json);
            
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            log.debug(e.getLocalizedMessage(), e);
        }

    }
}
