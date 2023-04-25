# Importing Data in a Neo4J-based Knowledge Graph
The API available via the [Python script](api/ts_api.py) is a wrapper for the 
[SPARQL over HTTP (SoH)](https://jena.apache.org/documentation/fuseki2/soh.html), 
[Fuseki HTTP Administrative Protocol](https://jena.apache.org/documentation/fuseki2/fuseki-server-protocol.html), and 
[Neosemantics](https://neo4j.com/labs/neosemantics/4.0/reference/). It allows one to query the triplestore and import 
the results into a Neo4J network.

## Running the API Server
```bash
pip install fastapi requests uvicorn requests_toolbelt sparqlwrapper python-multipart rdflib fastapi-login numpy python-jose[cryptography] passlib[bcrypt]
python api/ts_api.py
```
You can access the API GUI via http://localhost:8000/docs. The production version of the API GUI is available at 
https://triplestore.scaiview.com/api/docs. 

**NOTE**: By default, the credentials for all services have been encoded and are exported as environment 
variables/secrets for running in kubernetes. If you wish to run it locally, you will need to set up Neo4J and configure 
the credentials in the scripts.

## Available Methods
### Pipeline
These methods pertain to directly importing data from the triplestore into a Neo4J database.

#### `/import/{dataset}/{database}`
Import a set of triples from a `dataset` in the triplestore into a Neo4J `database`. If no `query` is given, then all 
triples will be imported, otherwise only the triples resulting from the `query` payload will be imported.

```bash
curl -X POST 'https://triplestore.scaiview.com/api/import/geonames/geodb' \
  --user username:password \
  -d '{"query": "SELECT * WHERE{ ?s ?p ?o . } LIMIT 100"}' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json'
```

### Query
These methods are for extracting information from the triplestore.

#### `/results/{dataset}/{result_format}`
With this tool you can select the `dataset` or "data graph" to query and in what `format` to have the results returned as
(JSON, TTL, XML, etc). You will submit your query using `POST` e.g.
```bash
curl -X 'POST' \
  'https://triplestore.scaiview.com/api/results/geonames/json' \
  -d '{"query": "SELECT * WHERE{ ?s ?p ?o . } LIMIT 100"}' \
  -H 'accept: application/json' \
  -H 'Content-Type: application/json'
```

##### Query Type and Result Format
Specific result formats are only available with certain query types:
* `SELECT`: xml, json, csv, tsv
* `ASK`: xml, json
* `CONSTRUCT`: rdfxml, n3, ttl
* `DESCRIBE`: rdfxml, ttl, n3

#### `/results/count/{dataset}`
Select the dataset and have the number of triples returned.

### Upload
These methods are for uploading data (as .ttl) to either a specific dataset in the triplestore or directly to a graph
database in Neo4J. You will need to use the triplestore credentials for verification and passed as `Authorization`.

#### `/upload/ts/{dataset}`
Upload data from a Turtle file to a `dataset` in the triplestore.

#### `/upload/neo4j/{database}`
Upload data from a Turtle file to a `database` in Neo4J.
