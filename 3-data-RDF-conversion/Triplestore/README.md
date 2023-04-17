# Loading converted RDF (TTL) into a Triplestore
For pre-selection purposes the converted and also free available datasets of interest were loaded into a triplestore database.

## TDB
A triplestore is a database for storing and retrieving triples over a query language like SparQL.
There are lots of freely available and also commercial triplestore applications on the web.
For a oversight (partly including benchmarks) visit [Wikipedia: LargeTripleStores](https://en.wikipedia.org/wiki/Triplestore), for example.

### Apache Jena Fuseki
[Apache Jena fuseki](https://jena.apache.org/documentation/fuseki2/) is a SPARQL server integrated with [TDB](https://jena.apache.org/documentation/tdb/index.html) 
to provide a transactional persistent storage layer.

### Semantic Computing Research Group
The [Semantic Computing Research Group (SeCo)](https://seco.cs.aalto.fi/) 
provides a Docker image which uses Apache Jena Fuseki and Apache TDB as the underlying triplestore database.
While the usage of [Apache Fuseki Docker](https://jena.apache.org/documentation/fuseki2/fuseki-docker.html) is also possible 
the [SeCo image](https://hub.docker.com/r/secoresearch/fuseki/) provides a more configurable and more performant application
with a useful and stable UI.

## Triples
A triple is a data entity in the form

```
subject - predicate - object
```

### Example:
```
PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
SELECT * WHERE {
  ?sub <http://flights.scai.fraunhofer.de/ontology#destination> <https://sws.geonames.org/3099426/> .
} LIMIT 1000
```

In this example we are looking for all **flights with destination** (<http://flights.scai.fraunhofer.de/ontology#destination>) **Paris, Charles de Gaulle Airport** which is defined by the geonames identifier (<https://sws.geonames.org/3099426/>).

The flight data is available in the [SCAI Triplestore](https://triplestore.scaiview.com) in dataset **flightaware**.

![img.png](img.png)

## Preparation and deployment of Fuseki Triplestore
To deploy the secoresearch/fuseki Docker image we used to build a pre-configured Docker image on [SCAI Artifactory](docker.arty.scai.fraunhofer.de) and deploy the image on our internal Kubernetes cluster.


