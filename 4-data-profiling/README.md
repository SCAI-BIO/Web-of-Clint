# Profiling RDF datasets

A simple way to profile an RDF dataset is to use SPARQL to query it. You can either download the RDF as file and use any RDF library (e.g. Apache Jena) to parse the date or you can directly query the SPARQL endpoint.

## count all triples http://rdfs.org/ns/void#triples 

```
SELECT (COUNT(*) as ?count) WHERE { ?s ?p ?o }
```


## count all subjects http://rdfs.org/ns/void#distinctSubjects

```
SELECT (COUNT(DISTINCT ?s) as ?count) WHERE { ?s ?p ?o }
```

## count all properties http://rdfs.org/ns/void#properties

```
SELECT (COUNT(DISTINCT ?p) as ?count) WHERE { ?s ?p ?o }
```

## count all classes http://rdfs.org/ns/void#classes

```
SELECT (COUNT(DISTINCT ?o) as ?count) WHERE { ?s <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> ?o }
```

## Listing all used vocabularies http://rdfs.org/ns/void#vocabulary

Every value of void:vocabulary must be a URI that identifies a vocabulary or ontology that is used in the dataset. These URIs can be found as follows:

* Take the URI of any class or property in the vocabulary.
* Strip the local name, that is, remove everything after the last “/” or “#”.
* If the URI now ends in a “#”, then also remove this trailing hash. (If it ends in a slash, the slash is kept.)

SPARQL query classes:

```
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> 
SELECT ?type (SAMPLE(?s) AS ?example) ");
WHERE { 
  SERVICE <endpoint> {
    ?s a ?type.");
  }
}
GROUP BY ?type
```

SPARQL query properties:

```
SELECT DISTINCT ?p 
WHERE { 
  ?s ?p ?o.
} 
```
