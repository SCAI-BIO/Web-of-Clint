# CLINT – the Web of publiC heaLth INtelligence daTa

## Purpose:
As we have seen in the COVID-19 pandemic, there is an urgent need to monitor health threats on a global scale. Public health intelligence (PHI) is the process of gathering and monitoring information on public health events. The main challenge is in the integration and timely analysis of the massive, globally dispersed data. The primary goal was to create a proof-of-concept to use existing semantic web technologies to integrate heterogeneous data relevant to pandemic intelligence.

## Methodology:
The approach of a formed consortium has been to query and interface the original, publically available sources (such as EIOS news threads, mass transport data, epidemiologic line data, geographical reference data).  A workflow was developed to automatically convert the data into the RDF format, describe and profile each data set using VoID descriptors, and to semantically link and map the data using existing domain ontologies and the SKOS framework. Existing SPARQL endpoints (e.g. from Linked open data cloud) were used together with the locally converted loaded RDF data to extract an *ad hoc* dedicated knowledge graph.

## Findings:
To demonstrate, we focused on a concrete example of a COVID-19 outbreak that occurred after an international football game in Italy in early 2020. We assembled a time slice of 1 year of news and media articles (29 mio web feeds from EIOS), 2.3 million European flights, 7 million COVID-19 cases from reported line data, zz geo information, and ???. All datasets were converted to RDF and mapped to 20 different ontologies resulting in a knowledge graph that was imported into Neo4j for subsequent analysis (xx nodes and yy edges).

## Value:
We showed that it is possible to assemble a vast knowledge graph from various resources within a short time frame in order to analyse a scenario of a mass spreading event using existing semantic web approaches. The developed tools and workflows will be made public on GitHub. The future challenge lies in predicting and preventing these kind of events. Currently, we are developing graph-based anomaly algorithms for the early detection of global health threats.
