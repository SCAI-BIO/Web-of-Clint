package de.fraunhofer.scai.bio.eiostooling.rdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.jena.atlas.logging.Log;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.ttl.turtle.TurtleParseException;
import org.apache.jena.ttl.turtle.TurtleReader;
import org.jsonschema2pojo.DefaultGenerationConfig;
import org.jsonschema2pojo.GenerationConfig;
import org.jsonschema2pojo.Jackson2Annotator;
import org.jsonschema2pojo.SchemaGenerator;
import org.jsonschema2pojo.SchemaMapper;
import org.jsonschema2pojo.SchemaStore;
import org.jsonschema2pojo.SourceType;
import org.jsonschema2pojo.rules.RuleFactory;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.sun.codemodel.JCodeModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LODTooling {

    // http://cas.lod-cloud.net/versions/2022-11-03/lod-data.json
    // http://cas.lod-cloud.net/datasets
    //http://cas.lod-cloud.net/rdf/geonames-semantic-web?format=ttl

    /**
     * download and validate the void descriptor - fixes some problems which cause the validator to fail
     * 
     * @param datasetName
     * @return <code>boolean</boolean> success
     * @throws MalformedURLException
     * @throws IOException
     */
    public static boolean fetchVoid(String datasetName) throws MalformedURLException, IOException {

        String FILE_URL = "http://cas.lod-cloud.net/rdf/"+ datasetName + "?format=ttl";
        String FILE_NAME = "./src/main/resources/output/void/void_" + datasetName;

        try {
            FileUtils.copyURLToFile(
                    new URL(FILE_URL), 
                    new File(FILE_NAME + ".sik"), 
                    1000, 
                    1000);

            try {
                String content = FileUtils.readFileToString(new File(FILE_NAME + ".sik"), "UTF-8");

                // zap all spaces with '+'
                String oldContent;
                do {
                    oldContent = content;
                    content = content.replaceAll("(?s)(<[^>]*)(\\s+)([^<]*>)", "$1+$3"); // void:target   <http://lod-cloud.net/dataset/European Patent+Information>
                } while(!oldContent.equals(content));

                // deal with curly brackets
                content = content
                        .replaceAll("(?s)sparql\\?([^<]*>)", "sparql>") // dcat:distribution    [ dcat:accessURL  <http://www.zaragoza.es/datosabiertos/sparql?query=CONSTRUCT+{%0D%0A++%3Fcatalogo+a+%3Chttp%3A%2F%2Fvocab.deri.ie%2Fdcat%23Catalog%3E%3B%0D%0A+++%3Chttp%3A%2F%2Fvocab.deri.ie%2Fdcat%23dataset%3E+%3Fdataset.%0D%0A++%3Fdataset+%3Chttp%3A%2F%2Fvocab.deri.ie%2Fdcat%23distribution%3E+%3Fdistribucion%3B%0D%0A+++%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Fterms%2FaccrualPeriodicity%3E+%3Ffrecuencia%3B%0D%0A+++%3Fp+%3Fo.%0D%0A++%3Ffrecuencia+%3Ffrec_p+%3Ffrec_o+.%0D%0A++%3Fdistribucion+%3Fdist_p+%3Fdist_o+.%0D%0A}%0D%0AWHERE+{%0D%0A++%3Fcatalogo+a+%3Chttp%3A%2F%2Fvocab.deri.ie%2Fdcat%23Catalog%3E%3B%0D%0A+++%3Chttp%3A%2F%2Fvocab.deri.ie%2Fdcat%23dataset%3E+%3Fdataset.%0D%0A++%3Fdataset+%3Chttp%3A%2F%2Fvocab.deri.ie%2Fdcat%23distribution%3E+%3Fdistribucion%3B%0D%0A+++%3Chttp%3A%2F%2Fpurl.org%2Fdc%2Fterms%2FaccrualPeriodicity%3E+%3Ffrecuencia%3B%0D%0A+++%3Fp+%3Fo.%0D%0A++%3Ffrecuencia+%3Ffrec_p+%3Ffrec_o+.%0D%0A++%3Fdistribucion+%3Fdist_p+%3Fdist_o+.%0D%0A}&format=application/x-turtle> ] ;
                        .replaceAll("(?s)/\\{.*\\}>", "/>") // dcat:accessURL  <http://www.open-biomed.org.uk/service/flybase/features/name/{name}> 
                        ;

                // deal with hard coded errors
                content = content
                        .replaceAll("%3Chttp://", "")   // void:exampleResource  <http://%3Chttp://purl.org/heritagedata/schemes/eh_period/concepts/RO.rdf> ;
                        .replaceAll("\\+›\\+journal_pdf", "")   // foaf:homepage        <https://www.iraj.in+›+journal_pdf> .
                        .replaceAll(":databnf@pef.bnf.fr/DATA/databnf_all_rdf_xml.tar.gz","")   // dcat:distribution     [ dcat:accessURL  <ftp://databnf:databnf@pef.bnf.fr/DATA/databnf_all_rdf_xml.tar.gz> ] ;
                        ;

                FileUtils.writeStringToFile(new File(FILE_NAME + ".ttl"), content, "UTF-8");
            } catch (IOException e) {
                throw new RuntimeException("Generating file failed", e);
            }

            TurtleReader turtleReader = new TurtleReader();
            Model model = ModelFactory.createDefaultModel();
            try {
                turtleReader.read(model, new FileReader(FILE_NAME + ".ttl"), null);
                model.setNsPrefix("lod", "http://lod-cloud.net/dataset/");
                RDFUtils.writeModelToFile(model, "./src/main/resources/output/void/", "void_" + datasetName, "TTL");

            } catch (IOException e) {
                Log.debug(e, e.getLocalizedMessage());
                throw new RuntimeException(e);
            } catch (TurtleParseException e) {
                log.error("Problem with " + FILE_NAME + ".ttl");
                Log.debug(e, e.getLocalizedMessage());
                throw new RuntimeException(e);            
            }

            new File(FILE_NAME + ".sik").delete();
            return true;

        } catch (FileNotFoundException e) {
            log.error(" >> Couldn't get " + datasetName);
        } catch (IOException e) {
            log.error(" >> Couldn't get " + datasetName);
        }

        return false;

    }

    public static void convertJsonToJavaClass(String inputJson, File outputJavaClassDirectory, String packageName, String javaClassName) 
            throws IOException {
        JCodeModel jcodeModel = new JCodeModel();

        GenerationConfig config = new DefaultGenerationConfig() {
            @Override
            public boolean isGenerateBuilders() {
                return true;
            }

            @Override
            public SourceType getSourceType() {
                return SourceType.JSON;
            }
        };

        SchemaMapper mapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());
        mapper.generate(jcodeModel, javaClassName, packageName, inputJson);

        jcodeModel.build(outputJavaClassDirectory);
    }

    /**
     * @param jsonDataSourceString
     * @throws IOException 
     * @throws MalformedURLException 
     */
    public static void downloadVoIDfomList(String jsonDataSourceString) throws MalformedURLException, IOException {
        String jsonpathIdentifierPath = "$";

        DocumentContext jsonContext = JsonPath.parse(jsonDataSourceString);
        Map<String,Object> jsonpathIdentifier = jsonContext.read(jsonpathIdentifierPath);

        int success = 0;
        int failure = 0;

        for(String datasetName : jsonpathIdentifier.keySet()) {
            if(fetchVoid(datasetName)) {
                success++;
            } else {
                failure++;
            }
        }

        log.info("success " + success + ", failure: " + failure);

    }


    public static void listEndpoints(String jsonDataSourceString) throws MalformedURLException, IOException {
        String jsonpathIdentifierPath = "$[*]['sparql'][*]['access_url']";

        DocumentContext jsonContext = JsonPath.parse(jsonDataSourceString);
        List<String> jsonpathIdentifier = jsonContext.read(jsonpathIdentifierPath);

        for(String endpoint : jsonpathIdentifier) {
            log.info(endpoint);            
        }

    }

}
