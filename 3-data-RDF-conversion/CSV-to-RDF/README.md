# Converting CSV to RDF (TTL)
Converting the row entries of a CSV file to an RDF compliant format requires mapping the properties or columns of the 
CSV file to specific ontologies as well as mapping the values to specific URIs. Ideally, columns referring to the same identifiers or information will be mapped 
to the same ontologies/URIs across data models.

## Structure
For simplicity, each row will be mapped to a unique instance and serve as the pivotal node to which all column 
information is connected to i.e. for every row [i] in the CSV file, there will be some node labelled [i] linked 
to all literals derived from that row's column values.

### Input files
The tool requires four inputs:

* Output file name with the extension of required format
* A CSV file of the tabular data to be converted
* A JSON property mapping file which described how the columns in the CSV file should be mapped
* A CSV value-resource mapping file which directly converts specific values in the CSV data to URIs
* [Optional] The name of the dataset to which the generated TTL file should be imported into

The JSON mapping file must have the following format:

```json
{
  "@context": [
    "http://schema.org/"
  ],
  "nodeLabel": "Dataset",                                 // node label to be displayed in Neo4j 
  "tableSchema": {
    "columns": [                                          // list of columns in the same order as in CSV and its types
      {
        "name": "column_name",    
        "propertyUrl": "http://schema.org/property_url",
        "datatype": "datatype",                           // XML Schema data types
        "uriref": "yes/no"                                // 'Yes' if the data is not a literal
      }
    ]
  }
}
```
The CSV value-resouce mapping file has the following format:
```
value,resource_uri
<value1>,<resource_uri1>
<value2>,<resource_uri2>
...
```

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