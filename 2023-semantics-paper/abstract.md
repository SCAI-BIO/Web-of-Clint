# CLINT – the Web of publiC heaLth INtelligence daTa

## Purpose:
As we have seen in the COVID-19 pandemic there is an urgent need to monitor health threats on a global scale. Public health intelligence (PHI) is the process of gathering and monitoring information on public health events. The main challenge lays in the integration and the timely analysis of the globally dispersed massive data. The main goal has been to create a proof-of-concept to use existing semantic web technologies to integrate heterogeneous data relevant for pandemic intelligence.

## Methodology:
The approach of a formed consortium has been to query and interface the original publically available sources (like EIOS news threads, mass transport data, epidemiologic line data, geographical reference data).  A workflow has been developed to automatically convert the data into RDF, to describe and profile each data set using VoID descriptors, to semantically link and map the data using existing domain ontologies and the SKOS framework. Existing SPARQL endpoints (e.g. from Linked open data cloud) have been queried together with the locally converted loaded RDF data to extract ad hoc a dedicated knowledge graph.

## Findings:
We took a concrete example of a COVID-19 outbreak after an international football game in early 2020 in Italy. We have assembled a time slice of 1 year of news and media articles (29 mio web feeds from EIOS), of xx European flights, of yy COVID-19 cases from reported line data, zz geo information, and of ??? Converted all datasets to RDF, linked and mapped to 20  ontologies and extracted a knowledge graph which has been loaded into Neo4j for analysis (xx nodes and yy edges).

## Value:
We could show that it is possible to assemble a vast knowledge graph from various resources within a short time frame in order to analyse a scenario of a mass spreading event using existing semantic web approaches. The developed tools and workflows will be made public on github. The future challenge lies in predicting and preventing these kind of events. Currently we are developing graph based anomaly algorithms for the early detection of global health threats.
