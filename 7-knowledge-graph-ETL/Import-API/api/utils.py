"""Set of utility methods for the ETL API."""
import os
import requests

from SPARQLWrapper.SPARQLExceptions import QueryBadFormed, EndPointNotFound
from SPARQLWrapper import SPARQLWrapper, JSON, TURTLE
from rdflib import Graph

from constants import TS_HOST, TS_PWD, TS_USER
from models import InvalidQueryTypeException, MissingDatasetException


def create_sparql_wrapper_for_triplestore(dataset: str):
    """Create a sparqlwrapper object for querying a specific dataset in the triplestore."""
    api_url = TS_HOST + dataset + "/query"
    print(api_url)
    sparql_wrapper = SPARQLWrapper(api_url)
    sparql_wrapper.setCredentials(user=TS_USER, passwd=TS_PWD)
    return sparql_wrapper


def count_triples_in_dataset(
        dataset: str,
) -> int:
    """Returns the number of triples in a given dataset."""
    sparql_wrapper = create_sparql_wrapper_for_triplestore(dataset=dataset)
    sparql_wrapper.setReturnFormat(JSON)
    count_query = "SELECT (COUNT(*) as ?count) WHERE { ?s ?p ?o}"
    sparql_wrapper.setQuery(count_query)
    query_result = query_and_convert(sw=sparql_wrapper)
    count_result = int(query_result["results"]["bindings"][0]["count"]["value"])
    return count_result


def get_datasets_in_triplestore():
    """Get a list of existing datasets in the triplestore."""
    dataset_url = TS_HOST + "$/datasets"
    resp = requests.get(dataset_url, auth=(TS_USER, TS_PWD))
    dataset_dicts = resp.json()["datasets"]
    datasets = [x['ds.name'].strip("/") for x in dataset_dicts]
    return dataset_url, datasets


def generate_results(dataset: str, count: int, chunk_size: int = 200000):
    """Create a generator that returns batches of results using a SparqlWrapper object."""
    offset = 0
    result_spql = create_sparql_wrapper_for_triplestore(dataset=dataset)
    result_spql.setReturnFormat(TURTLE)
    result_query = f"DESCRIBE * WHERE {{ ?s ?p ?o . }} LIMIT {chunk_size}"
    g = Graph()
    while offset < count:
        result_spql.setQuery(result_query)
        result_chunk = result_spql.queryAndConvert()
        g.parse(data=result_chunk, format=TURTLE)
        offset += chunk_size  # iterate the offset

        return g.serialize(format=TURTLE)


def query_and_convert(sw: SPARQLWrapper):
    """Query the triplestore and convert the response, raise error if forseen issues occur. Must pass a SPARQLWrapper
    with a query already set."""
    try:
        results = sw.queryAndConvert()
        return results

    except QueryBadFormed:
        raise InvalidQueryTypeException()

    except EndPointNotFound:
        raise MissingDatasetException()


def file_iterator(file_path: str, chunk_size: int = 4096):
    with open(file_path, mode="rb") as file:
        while True:
            data = file.read(chunk_size)
            if not data:
                break
            yield data


def delete_file(file_path: str):
    os.remove(file_path)
