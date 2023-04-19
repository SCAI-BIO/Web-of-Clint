"""FastAPI response models and class definitions."""
import json
from typing import Optional
from pydantic import BaseModel
from typing import List
from enum import Enum

from SPARQLWrapper import JSON, TURTLE, XML, RDF, CSV, TSV, JSONLD


class InvalidQueryTypeException(Exception):
    pass


class MissingDatasetException(Exception):
    pass


class SparqlQuery(BaseModel):
    """SPARQL query for the dataset."""
    query: str = "SELECT * WHERE{ ?s ?p ?o . } LIMIT 100"


class ReturnFormat(str, Enum):
    """Data format for the returns query results."""
    json = JSON
    ttl = TURTLE
    xml = XML
    # n3 = N3
    rdf = RDF
    # rdfxml = RDFXML
    csv = CSV
    tsv = TSV
    jsonld = JSONLD


class TripleCount(BaseModel):
    count: int


class Neo4jImportResponse(BaseModel):
    terminationStatus: str
    triplesLoaded: int
    triplesParsed: int
    namespaces: Optional[list]
    extraInfo: str
    callParams: dict


# Neo4J Semantics Models
class HandleVocabUris(str, Enum):
    shorten = "SHORTEN"
    shorten_strict = "SHORTEN_STRICT"
    ignore = "IGNORE"
    map = "MAP"
    keep = "KEEP"


class HandleMultival(str, Enum):
    overwrite = "OVERWRITE"
    array = "ARRAY"


class HandleRDFTypes(str, Enum):
    labels = "LABELS"
    nodes = "NODES"
    labels_and_nodes = "LABELS_AND_NODES"


class ConfigOption(BaseModel):
    """Neosemantic graph configuration options."""
    handleVocabUris: HandleVocabUris = HandleVocabUris.ignore
    handleMultival: HandleMultival = HandleMultival.array
    multivalPropList: List[str] = []
    keepLangTag: bool = True
    handleRDFTypes: HandleRDFTypes = HandleRDFTypes.labels
    keepCustomDataTypes: bool = False
    customDataTypePropList: List[str] = []
    applyNeo4jNaming: bool = False

    @classmethod
    def __get_validators__(cls):
        yield cls.validate_to_json

    @classmethod
    def validate_to_json(cls, value):
        if isinstance(value, str):
            return cls(**json.loads(value))
        return value

    def cypher_body(self):
        """Convert params and values to a cypher body."""
        body = f"""{{handleVocabUris: "{self.handleVocabUris}",
handleMultival: "{self.handleMultival}",
multivalPropList: {self.multivalPropList},
keepLangTag: {json.dumps(self.keepLangTag)},
handleRDFTypes: "{self.handleRDFTypes}",
keepCustomDataTypes: {json.dumps(self.keepCustomDataTypes)},
customDataTypePropList: {self.customDataTypePropList},
applyNeo4jNaming: {json.dumps(self.applyNeo4jNaming)}}}"""
        return body

    def prep_payload(self, query: str):
        """Add query and header to fetch cypher."""
        cypher_body = self.cypher_body()[:-1]
        header_query_cypher = f'headerParams: {{ Accept: "application/turtle"}}, payload: "query=" + apoc.text.urlencode("{query}")}}'
        return cypher_body + ",\n" + header_query_cypher


class SetConfig(BaseModel):
    database: str
    config: ConfigOption
