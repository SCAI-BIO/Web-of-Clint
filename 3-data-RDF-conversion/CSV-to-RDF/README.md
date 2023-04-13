# Converting CSV to RDF (TTL)
Converting the row entries of a CSV file to an RDF compliant format requires mapping the properties or columns of the 
CSV file to specific ontologies. Ideally, columns referring to the same identifiers or information will be mapped 
to the same ontologies across data models.

## Structure
For simplicity, each row will be mapped to a unique instance and serve as the pivotal node to which all column 
information is connected to i.e. for every row [i] in the CSV file, there will be some node labelled [i] linked 
to all literals derived from that row's column values.

## Conversion Script
The `csv_2_rdf.py` script allows one to take an input CSV file and a JSON file containing information which maps 
the columns of the CSV file to individual ontologies, and convert them to a turtle file. The structure for this 
metadata JSON file follows the [W3C: CSV on the Web guidelines](https://www.w3.org/TR/tabular-data-primer/).

### Example
<!--- TODO: Change example to flight data --->
```bash
$ pip install -r requirements.txt
$ python csv_2_rdf.py example/fluid_sample.csv example/fluid-metadata.json fluid.ttl
```