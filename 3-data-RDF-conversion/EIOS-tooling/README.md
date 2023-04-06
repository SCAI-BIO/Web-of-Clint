# EIOS-Tooling

small application to query the EIOS elastic search endpoint and convert the result into proper RDF

## commandline calls

arguments

```
usage: EIOS Tooling
 -?,--help                showing argumemts
 -c,--chunksize <arg>     how many documents in one chunk
 -i,--input <arg>         input filename
 -idx,--indexes <arg>     indexes to be queried as a list
 -ip,--inputpath <arg>    filepath to inputs
 -l,--limit <arg>         limit size of EIOS query
 -li,--listindexes        list all indexes
 -m,--mappings <arg>      mappings input file name
 -o,--output <arg>        output file name
 -op,--outputpath <arg>   filepath to outputs
 -pc,--curie              add curie to prefixes
 -q,--query <arg>         category to be searched
 -r,--RDF <arg>           RDF output format
 -s,--output              export SCAIView documents
 -zp,--zipProcess <arg>   input zip file
```

query the EIOS endpoint

```
 -q ./src/main/resources/input/query-all-2019.json -r TTL -s -l 5000000 -c 1000 -op e: -idx "eios-items_2019"
```

process a zip file from a query result -> output RDF turtle and SCAIView documents

```
 -zp "e:/query-all/eios-items_2020-08.zip" -op "e:/query-all-out/"  -r TTL -s
```