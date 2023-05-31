package de.fraunhofer.scai.bio.eiostooling.rdf;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RDFProfiling {

    public static void enrichVoidFromDir(String directory) throws IOException {

        for (File file : FileUtils.listFiles(new File(directory), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE)) {
            RDFUtils.writeModelToFile(
                    enrichVoidFile(file), 
                    "./src/test/resources/void", 
                    file.getName(), "TTL");
        }

    }

    /**
     * search for void:endpoint and query it
     * 
     * @param file
     * @return <code>Model</code> if enriched, else <code>null</code>
     */
    public static Model enrichVoidFile(File file) {

        Model model = null;

        try {
            model = RDFUtils.readModelFromFile(file);
            model.setNsPrefix("void", "http://rdfs.org/ns/void#");
            model.setNsPrefix("rdf", "");
            model.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");

            String sparql = "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
                    + "SELECT ?dataset WHERE {"
                    + "  ?dataset rdf:type ?o . " 
                    + "}";

            String dataset = null;

            List<List<RDFNode>> results = RDFUtils.queryModel(model, sparql);

            if(results != null) {
                for(List<RDFNode> result : results  ) {
                    if(result != null) {
                        dataset = result.get(0).toString();
                    }
                }
            }

            sparql = "PREFIX void: <http://rdfs.org/ns/void#> " 
                    + "SELECT ?endpoint "
                    + "WHERE {"
                    + " ?s void:sparqlEndpoint ?endpoint . "
                    + "}";

            results = RDFUtils.queryModel(model, sparql);

            if(results != null) {
                for(List<RDFNode> result : results) {
                    if(result != null) {
                        String endpoint = result.get(0).toString();
                        return RDFProfiling.profileRemoteModel(endpoint, dataset);
                    }
                }
            }

        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
            log.debug(e.getLocalizedMessage(), e);
        }

        return null;
    }

    public static Model profileModel(Model model, String uri) {

        Model profileModel = ModelFactory.createDefaultModel();

        profileModel.setNsPrefix("void", "http://rdfs.org/ns/void#");
        profileModel.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        profileModel.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
        profileModel.setNsPrefix("owl", "http://www.w3.org/2002/07/owl#");
        profileModel.setNsPrefix("xsd", "http://www.w3.org/2001/XMLSchema#");
        profileModel.setNsPrefix("dcterms", "http://purl.org/dc/terms/");
        profileModel.setNsPrefix("foaf", "http://xmlns.com/foaf/0.1/");

        Resource dataset = profileModel.createResource(uri);

        addCountStatement(profileModel, dataset, "http://rdfs.org/ns/void#triples", model, "SELECT (COUNT(*) as ?count) WHERE { ?s ?p ?o }");
        addCountStatement(profileModel, dataset, "http://rdfs.org/ns/void#distinctSubjects", model, "SELECT (COUNT(DISTINCT ?s) as ?count) WHERE { ?s ?p ?o }");
        addCountStatement(profileModel, dataset, "http://rdfs.org/ns/void#properties", model, "SELECT (COUNT(DISTINCT ?p) as ?count) WHERE { ?s ?p ?o }");
        addCountStatement(profileModel, dataset, "http://rdfs.org/ns/void#classes", model, "SELECT (COUNT(DISTINCT ?o) as ?count) WHERE { ?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?o }");

        extractVocabularies(model, profileModel, dataset, null);
        extractSamples(model, profileModel, dataset, null);

        return profileModel;
    }

    /**
     * @param endpoint
     * @param uri
     * @return
     */
    public static Model profileRemoteModel(String endpoint, String uri) {

        Model profileModel = ModelFactory.createDefaultModel();

        profileModel.setNsPrefix("void", "http://rdfs.org/ns/void#");
        profileModel.setNsPrefix("rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
        profileModel.setNsPrefix("rdfs", "http://www.w3.org/2000/01/rdf-schema#");
        profileModel.setNsPrefix("owl", "http://www.w3.org/2002/07/owl#");
        profileModel.setNsPrefix("xsd", "http://www.w3.org/2001/XMLSchema#");
        profileModel.setNsPrefix("dcterms", "http://purl.org/dc/terms/");
        profileModel.setNsPrefix("foaf", "http://xmlns.com/foaf/0.1/");

        Resource dataset = profileModel.createResource(uri);

        addCountStatement(profileModel, dataset, "http://rdfs.org/ns/void#triples", profileModel, "SELECT (COUNT(*) as ?count) WHERE { SERVICE <"+endpoint+"> { ?s ?p ?o } } LIMIT 100000");
        addCountStatement(profileModel, dataset, "http://rdfs.org/ns/void#distinctSubjects", profileModel, "SELECT (COUNT(DISTINCT ?s) as ?count) WHERE { SERVICE <"+endpoint+"> { ?s ?p ?o } } LIMIT 100000");
        addCountStatement(profileModel, dataset, "http://rdfs.org/ns/void#properties", profileModel, "SELECT (COUNT(DISTINCT ?p) as ?count) WHERE { SERVICE <"+endpoint+"> { ?s ?p ?o } } LIMIT 100000");
        addCountStatement(profileModel, dataset, "http://rdfs.org/ns/void#classes", profileModel, "SELECT (COUNT(DISTINCT ?o) as ?count) WHERE { SERVICE <"+endpoint+"> { ?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?o } } LIMIT 100000");

        extractVocabularies(profileModel, profileModel, dataset, endpoint);
        extractSamples(profileModel, profileModel, dataset, endpoint);

        return profileModel;
    }

    // take the 10 nodes with the highest out degree as example
    private static void extractSamples(Model model, Model profileModel, Resource dataset, String endpoint) {
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT ?s (COUNT(?o) as ?deg) ");
        sb.append("WHERE { ");

        if(endpoint != null) {
            sb.append("SERVICE <"+endpoint+"> { ");
        }

        sb.append("  ?s ?p ?o."); 
        sb.append("  FILTER (!isBlank(?s)) ");
        sb.append("} ");


        if(endpoint != null) {
            sb.append(" } ");
        }

        sb.append("GROUP BY ?s ");
        sb.append("ORDER BY DESC(?deg) ");
        sb.append("LIMIT 10");

        Property p = profileModel.createProperty("http://rdfs.org/ns/void#exampleResource");
        List<List<RDFNode>> results = RDFUtils.queryModel(model, sb.toString());

        if(results != null) {
            for(List<RDFNode> row : results) {
                profileModel.add(dataset, p, row.get(0).asResource());
            }
        }
    }

    //        Every value of void:vocabulary must be a URI that identifies a vocabulary or ontology that is used in the dataset. These URIs can be found as follows:
    //
    //            Take the URI of any class or property in the vocabulary.
    //            Strip the local name, that is, remove everything after the last “/” or “#”.
    //            If the URI now ends in a “#”, then also remove this trailing hash. (If it ends in a slash, the slash is kept.)
    private static void extractVocabularies(Model model, Model profileModel, Resource dataset, String endpoint) {

        StringBuilder sb = new StringBuilder();

        sb.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "); 
        sb.append("SELECT ?type (SAMPLE(?s) AS ?example) ");
        sb.append("WHERE { "); 

        if(endpoint != null) {
            sb.append("SERVICE <"+endpoint+"> { ");
        }

        sb.append("  ?s rdf:type ?type.");
        sb.append("} ");

        if(endpoint != null) {
            sb.append(" } ");
        }

        sb.append("GROUP BY ?type");

        String query = sb.toString();

        Property p = profileModel.createProperty("http://rdfs.org/ns/void#vocabulary");

        List<List<RDFNode>> results = RDFUtils.queryModel(model, query);
        if(results != null) {
            for(List<RDFNode> row : results) {
                if(row.get(0).isResource()) {
                    String vocab = row.get(0).asResource().getNameSpace().replaceAll("#$", "");
                    profileModel.add(dataset, p, profileModel.createResource(vocab));
                }
            }
        }

        query = "SELECT DISTINCT ?p " + 
                "WHERE { " + 
                "  ?s ?p ?o." + 
                "} "; 

        results = RDFUtils.queryModel(model, query);
        if(results != null) {
            for(List<RDFNode> row : results) {
                String vocab = row.get(0).asResource().getNameSpace().replaceAll("#$", "");
                profileModel.add(dataset, p, profileModel.createResource(vocab));
            }
        }
    }

    private static void addCountStatement(Model outModel, Resource dataset, String property, Model model, String sparql) {
        outModel.add(
                outModel.createLiteralStatement(dataset, 
                        outModel.createProperty(property), 
                        RDFUtils.countModel(model, sparql))
                );
    }
}
