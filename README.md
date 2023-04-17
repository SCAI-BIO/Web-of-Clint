# Web-of-Clint

Web of publi**C** hea**L**th **IN**telligence da**T**a tooling

This repo is meant to add all documentation, scripts, pieces of code which we will publically make available after the WHO project has ended.

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
