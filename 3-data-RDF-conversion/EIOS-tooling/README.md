# EIOS-Tooling

small application to query the EIOS elastic search endpoint and convert the result into proper RDF

## installation

* clone the repo from [https://github.com/SCAI-BIO/Web-of-Clint.git](https://github.com/SCAI-BIO/Web-of-Clint.git)
* locate the folder of EIOS-tooling in folder 3-data-RDF-conversion
* switch to main branch
* run maven to build the package (check the corresponding [pom.xml](https://github.com/SCAI-BIO/Web-of-Clint/blob/main/3-data-RDF-conversion/EIOS-tooling/pom.xml))

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

### query the EIOS endpoint

```
java -jar ./target/eiostooling-0.0.1-SNAPSHOT.jar -q ./src/main/resources/input/query-all-2019.json -r TTL -s -l 5000000 -c 1000 -op e: -idx "eios-items_2019"
```
an example of a resulting document can be found here: [eios_401862973_pt.json](https://github.com/SCAI-BIO/Web-of-Clint/blob/main/3-data-RDF-conversion/EIOS-tooling/src/main/resources/input/eios_401862973_pt.json)

larger chunks are zipped - a small test.zip can be found [here](https://github.com/SCAI-BIO/Web-of-Clint/blob/main/3-data-RDF-conversion/EIOS-tooling/src/main/resources/input/test.zip) 


### process a zip file from a query result -> output RDF turtle and SCAIView documents

```
java -jar ./target/eiostooling-0.0.1-SNAPSHOT.jar -zp "./input/eios-items_2020-08.zip" -op "./output/"  -r TTL -s
```
this will produce four different RDF files in zipped [Turtle format](https://www.w3.org/TR/turtle/) (c.f. [output](https://github.com/SCAI-BIO/Web-of-Clint/tree/main/3-data-RDF-conversion/EIOS-tooling/src/main/resources/output))
 * the orginal EIOS document converted into RDF, i.e. the meta data (author, publicher, date, ...) and the content (title, text, ...)
 * a mentioning of relevant ecategories in the text (NLP taggings by the EIOS pipeline), i.e. a text snippet (sentence) where a EIOS category has been tagged and mapped to an ontology (the mappings of the categories are loaded from the resources using a mapping file)
 * a mapping of all structured data to ontology conceptsusing the Web Annotation Ontology [AO](https://www.w3.org/ns/oa) with full provenance using [PROV-O](https://www.w3.org/TR/prov-o/)
 * a SCAIView document (json) whichi can be used to further process and text mine the EIOS documents (add ontology annotations and mappings).The annotated SCAIView results can be converted into proper RDF using this tooling 

#### schema and example of the RDF document


```
@prefix schema: <http://schema.org/> .
@prefix gn:    <https://sws.geonames.org/> .
@prefix skos:  <http://www.w3.org/2004/02/skos/core#> .
@prefix rdfs:  <http://www.w3.org/2000/01/rdf-schema#> .
@prefix dbpedia: <http://dbpedia.org/resource/> .
@prefix scai:  <https://www.scai.fraunhofer.de/> .
@prefix dc11:  <http://purl.org/dc/elements/1.1/> .
@prefix oa:    <http://www.w3.org/ns/oa#> .
@prefix rdf:   <http://www.w3.org/1999/02/22-rdf-syntax-ns#> .
@prefix dcterms: <http://purl.org/dc/terms/> .
@prefix wgs84_pos: <http://www.w3.org/2003/01/geo/wgs84_pos#> .
@prefix prov:  <http://www.w3.org/ns/prov#> .
@prefix foaf:  <http://xmlns.com/foaf/0.1/> .
@prefix who:   <https://int.who/> .

who:laverdad-46f0b162341780f4b2075635490579e4
        a       <https://schema.org/webFeed> .

who:burgosconecta-46f0b162341780f4b2075635490579e4
        a                    <https://schema.org/webFeed> ;
        prov:wasDerivedFrom  who:laverdad-46f0b162341780f4b2075635490579e4 .

who:Document_401863008
        a                    <https://schema.org/TextDigitalDocument> ;
        dc11:coverage        "European News" ;
        dc11:creator         <https://www.who.int/initiatives/eios> ;
        dc11:date            "2023-01-02T15:40:00Z" ;
        dc11:description     "Los españoles cada vez pasan menos tiempo delante de la televisión tradicional. Con la vuelta a la normalidad, y tras el fin de la época más dura de la pandemia, que sirvió para reactivar momentáneamente un consumo televisivo que iba en decadencia, la audiencia opta por otro tipo de contenidos alternativos al que ofrecen las cadenas tradicionales."@es , "The spanish each time spend less time in front of the traditional television. With the return to normality, and after the end of the time most of the pandemic, which served to revive momentarily a television consumption that was in decline, the hearing opting for another type of content that offer alternative to traditional chains."@es ;
        dc11:format          "application/json" ;
        dc11:identifier      "EIOS:401863008" ;
        dc11:language        "Spanish; Castilian" ;
        dc11:publisher       <https://www.burgosconecta.es/> ;
        dc11:relation        "laverdad-46f0b162341780f4b2075635490579e4" ;
        dc11:source          <https://www.burgosconecta.es/culturas/tv/espectadores-television-consumo-20230102145136-ntrc.html> ;
        dc11:subject         "EMR" , "AF" , "Gathering" , "EUR" , "ES:Spain" , "Qatar" , "EURO" , "EU" , "Marruecos" , "QA:Qatar" , "MA:Morocco" , "AS" , "EMRO" , "Madrid" ;
        dc11:title           "Viewers see less television: consumption falls to its lowest in three decades"@en , "Los espectadores ven menos televisión: el consumo cae a su cifra más baja en tres décadas"@es ;
        dc11:type            "webnews" ;
        schema:text          "Los españoles cada vez pasan menos tiempo ..."@es ;
        prov:wasDerivedFrom  who:burgosconecta-46f0b162341780f4b2075635490579e4 .
        
<https://www.burgosconecta.es/>
        a                           dc11:publisher ;
        rdfs:label                  "burgosconecta"@es ;
        dc11:coverage               "National" ;
        dc11:date                   "2019-10-16T12:45:21.4250000Z" ;
        dc11:identifier             "10672" ;
        dcterms:accrualPeriodicity  "daily" , "2" ;
        foaf:name                   "burgosconecta"@es .
```

