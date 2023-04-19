# Web-of-Clint

Web of publi**C** hea**L**th **IN**telligence da**T**a tooling

This repo is meant to add all documentation, scripts, pieces of code which we will publically make available after the WHO project has ended.

## Semantic web and knowledge graph - the vision
Expert users, subject matter experts, public health intelligence practitioners, knowledge and data engineers will use fit-for-purpose web-based software application(s) for mapping and annotating data stored at different sources, as well as designing data graphs compliant with standard Semantic Web technologies and linked data design patterns. They will also design and maintain upper reference ontologies for linking domain ontologies in a global network of data, information, insights and knowledge for public health intelligence.
The expert users shall design knowledge graph data models and merge data from the global semantic network of data, information, insights and knowledge for answering questions of interest or specific analytic scenarios and for sharing the results of the knowledge-graph-powered analysis back to the global semantic network. (_cf. RFP p14_)

|Slogan |Description |Priority |Comment |
|--------|-------------|---------|-----------|
|FR-KGD |Expert user shall design a knowledge graph optimised for answering questions of interest or specific analytic scenarios |Must have |Expert user, knowledge engineer, will use a visual tool to design a labelled property graph data model for implementing a knowledge graph as a projection of global semantic web of data optimised for answering questions of interest or specific analytic scenarios|
|FR-SENG|User shall search the global network of data, information, insights and knowledge stored in variety of sources on the Web |Could have|User can use a search engine for finding and accessing linked data, information, insights and knowledge on the Web |
|FR-KGIN |User shall merge selected data, information, insights and knowledge on the Web within an existing knowledge graph for further analysis |Must have| User will use data linked in a knowledge graph, implemented using a labelled property graph database management system, for advanced analytics workflows and visualisation of results|

## increments
|id|title|description|
|--|----|------------|
|#01|data cataloging and prioritization| create a first list of prioritized datasets to work on| 
|#02|meta data creation| create VoID descriptors for each prio dataset, access the data, create mappings|
|#03|RDF conversion|convert prio datasets into proper RDF -> datathon| 
|#04|ontology mapping|prioritize and map between needed ontologies (SKOS)|

## some helpful links and resources

### since we agreed on using [semantic web](https://www.w3.org/standards/semanticweb/):
- The [RDF primer v1.1](https://www.w3.org/TR/rdf11-primer/)  is designed to provide the reader with the basic knowledge required to effectively use RDF. It introduces the basic concepts of RDF and shows concrete examples of the use of RDF.
- This document is an overview of [SPARQL 1.1](https://www.w3.org/TR/sparql11-overview/). It provides an introduction to a set of W3C specifications that facilitate querying and manipulating RDF graph content on the Web or in an RDF store.
- [RDF](https://www.w3.org/RDF/) is a directed, labeled graph data format for representing information in the Web. This specification defines the syntax and semantics of the [SPARQL query language](https://www.w3.org/TR/rdf-sparql-query/) for RDF.
- This web page is the home of the [LOD cloud diagram](http://linkeddata.org/). This image shows datasets that have been published in the [Linked Data] format.
 The dataset currently contains datasets with links (as of May 2020)[linked open data cloud](https://lod-cloud.net/)
- Describing Linked Datasets with the [VoID Vocabulary](https://www.w3.org/TR/void/). VoID is an RDF Schema vocabulary for expressing metadata about RDF datasets. It is intended as a bridge between the publishers and users of RDF data, with applications ranging from data discovery to cataloging and archiving of datasets.
- This [document defines the XML](https://www.w3.org/TR/rdf-syntax-grammar/] syntax for RDF graphs. 
- [SKOS ](https://www.w3.org/2004/02/skos/) is an area of work developing specifications and standards to support the use of knowledge organization systems (KOS) such as thesauri, classification schemes, subject heading lists and taxonomies within the framework of the Semantic Web 
-  The PROV Ontology ([PROV-O](https://www.w3.org/TR/prov-o/])) expresses the PROV Data Model [[PROV-DM](https://www.w3.org/TR/prov-o/#bib-PROV-DM)] using the OWL2 Web Ontology Language (OWL2) [[OWL2-OVERVIEW](https://www.w3.org/TR/prov-o/#bib-OWL2-OVERVIEW)]. It provides a set of classes, properties, and restrictions that can be used to represent and interchange provenance information generated in different systems and under different contexts. It can also be specialized to create new classes and properties to model provenance information for different applications and domains.

## software and services used in the project
- The [internally hosted triple store for testing](http://fuseki.scainet.k8s.bio.scai.fraunhofer.de/index.html) is based on Apache Jena Fuseki and it needs VPN to access it.
- The [RDForms VoID Editor](https://rdforms.com/editors/void/#about) is a simple editor to create a VoID descriptor for a data set. The resulting VoID decriptor in RDF/XML needs to be copied into a file.
- [RDForms](https://rdforms.org/#!index.md) ("RDF Forms") is a javascript library which main task is to make it easy to construct form-based RDF editors in a web environment. To accomplish this, RDForms relies on a templating mechanism that both describes how to generate a HTML-form and how to map specific expressions in a RDF graph to corresponding fields. RDForms can also be used to present and validate RDF graphs and drive a Linkded Data browser.
For the source code of the project visit [RDForms on Bitbucket](http://bitbucket.com/metasolutions/rdforms).
- You can use simple web based [RDF/XML validator service](https://www.w3.org/RDF/Validator/) or a [RDF Validator and Converter](http://rdfvalidator.mybluemix.net/) directly in the browser.
- The [Ontology Lookup Service](https://www.ebi.ac.uk/ols/index) (OLS) is a repository for biomedical ontologies that aims to provide a single point of access to the latest ontology versions. You can browse the ontologies through the website as well as programmatically via the OLS API. We will setup an internal instance of OLS:


## Story for the webinar and semantics paper
### Use case
Alfredo the public health intelligence officer needs to load flight data into the KG -> in the end he has loaded all FilghtAware data using our tooling

### steps to load flight data
1. data landscaping
1. data FAIRification (aka VoID)
1. turtle ETL
1. RDF profiling
1. mapping to open reference ontology
1. KG data model
1. KG ETL within Fuseki

### steps Alfredo performs
1. getting alert and decision flight data is needed
1. search for flight data sets in SCAIView
1. selects proper data set and starts ETL pipeline
1. analyses flight data in KG, after ETL has finished
