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
To deploy the secoresearch/fuseki Docker image we used to build a pre-configured Docker image on a dedicated Docker registry and deploy the image on our internal Kubernetes cluster.

```
cd path/to/secoresearch-fuseki
build -t "docker.example.com/secoresearch-fuseki:0.0.1" -t "docker.example.com/secoresearch-fuseki:latest" -f Dockerfile
```

This will create 2 Docker images, one with version "0.0.1" and on with version tag "latest".

After building the images you have to push them onto the dedicated docker registry:

```
docker push "docker.example.com/secoresearch-fuseki:0.0.1"
docker push "docker.example.com/secoresearch-fuseki:latest"
```

OR run the Docker images on your host:
```
docker run --rm -it -p 3030:3030 --name fuseki -e ADMIN_PASSWORD=[PASSWORD] -e ENABLE_DATA_WRITE=[true|false] -e ENABLE_UPDATE=[true|false] -e ENABLE_UPLOAD=[true|false] -e QUERY_TIMEOUT=[number in milliseconds] --mount type=bind,source="$(pwd)"/fuseki-data,target=/fuseki-base/databases secoresearch/fuseki
```
See further information at [secoresearch/fuseki](https://hub.docker.com/r/secoresearch/fuseki/).

To run secoresearch/fuseki on kubernetes:

```
cd path/to/kubernetes/secoreresearch-fuseki
kubectl apply -f .
```
