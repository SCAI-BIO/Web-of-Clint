import sys
import csv
import json
from rdflib import Graph, URIRef, Literal, Namespace, RDF
import numpy as np

def csv_to_ttl(csv_file, json_file, uri_file,output_file, output_format='ttl'):
    """Convert a CSV file to TTL using mapped metadata."""
    with open(json_file, "r") as jf:
        data = json.load(jf)

    g = Graph()
    data_ns = Namespace(data['@context'][0])
    xsd = Namespace("http://www.w3.org/2001/XMLSchema#")
    dataset = data_ns[data['nodeLabel']]
    temp = 0
    json_cols = data['tableSchema']['columns']
    g.bind("data_ns", data_ns)

    # Load resource URI mapping file
    uri_map = {}
    with open(uri_file) as f:
        reader = csv.reader(f)
        for row in reader:
            uri_map[row[0]] = row[1]

    with open(csv_file) as f:
        datar = csv.reader(f)
        cols = next(datar)

        if len(cols) != len(json_cols):
            return 'Not all the rows are mapped to an ontology, please check again.'

        else:
            for row_num, row in enumerate(datar, start=1):

                instance = data_ns[str(row_num)]
                g.add((instance, RDF.type, dataset))

                for i in range(len(json_cols)):
                    if json_cols[i]['uriref'] == 'no':
                        g.add(
                            (
                                instance, 
                                URIRef(json_cols[i]['propertyUrl']), 
                                Literal(row[i] if row[i] != '' else np.nan, datatype=xsd[json_cols[i]['datatype']])
                            )
                        )

                    else:
                        uri = uri_map.get(row[i])
                        if uri:
                            g.add(
                                (
                                    instance, 
                                    URIRef(json_cols[i]['propertyUrl']),
                                    URIRef(uri)
                                )
                            )
                        else:
                            g.add(
                                (
                                    instance,
                                    URIRef(data['tableSchema']['columns'][i]['propertyUrl']),
                                    URIRef(data_ns[str(row[i])])
                                )
                            )

    g.serialize(destination=output_file, format=output_format)
    return g


if __name__ == "__main__":
    csvf = sys.argv[1]
    jsonf = sys.argv[2]
    urif = sys.argv[3]
    output = sys.argv[4]
    csv_to_ttl(csv_file=csvf, json_file=jsonf, uri_file=urif ,output_file=output)
