import json
from typing import Union

from neo4j import GraphDatabase
from models import ConfigOption
from constants import TS_HOST, TS_USER, TS_PWD


class Neo4jConnection:

    def __init__(self, uri, user, pwd, database):
        self.__uri = uri
        self.__user = user
        self.__pwd = pwd
        self.database = database
        self.__driver = None

        try:
            self.__driver = GraphDatabase.driver(self.__uri, auth=(self.__user, self.__pwd), database=self.database)

        except Exception as e:
            print("Failed to create the driver:", e)

    def close(self):
        if self.__driver is not None:
            self.__driver.close()

    def query(self, query):
        assert self.__driver is not None, "Driver not initialized!"
        session = None

        try:
            session = self.__driver.session()
            resp = session.run(query)
            results = [dict(x) for x in resp]
            return results

        except Exception as e:
            error = {"error": e}
            print(error)
            return error

        finally:
            if session is not None:
                session.close()

    def init_graph_config(self):
        """Initialize the graph configuration."""
        self.query('CALL n10s.graphconfig.init({handleRDFTypes:"LABELS", handleVocabUris:"IGNORE"})')
        self.query(query="CREATE CONSTRAINT n10s_unique_uri IF NOT EXISTS FOR (r:Resource) REQUIRE r.uri IS UNIQUE")

    def get_graph_config(self) -> dict:
        """Get the graph configuration settings."""
        config_results = self.query("CALL n10s.graphconfig.show()")
        return {x["param"]: x["value"] for x in config_results}

    def set_graph_config(
            self,
            config: Union[ConfigOption, dict] = ConfigOption(),
    ):
        """Set the graph configuration for a database in Neo4J.

        If there is already data, configuration will not be applied.

        Args:
            config: graph configuration settings as defined at
            https://neo4j.com/labs/neosemantics/4.0/reference/#_graph_config_params_global_settings
        """
        self.check_graph_init()

        if isinstance(config, dict):
            config = ConfigOption.parse_obj(config)

        cypher_graphconfig = f"""CALL n10s.graphconfig.set({config.cypher_body()})"""

        resp = self.query(cypher_graphconfig)

        if "error" in resp:
            return resp

        else:
            return {x["param"]: x["value"] for x in resp}

    def check_graph_init(self):
        """Check whether the current graph database has been initialized and if not, initializes it."""
        current_config = self.get_graph_config()
        if not current_config:  # Initialize graph configuration if not present
            self.init_graph_config()

    def import_ttl_file(self, file_uri: str, config: Union[ConfigOption, dict] = ConfigOption()) -> dict:
        """_summary_
        Parameters
        ----------
        file_uri : str
            File path or URL

        config : dict | ConfigOption
            Configuration options for the graph database.

        Raises
        ------
        FileNotFoundError
            _description_
        """
        uri = f"file://{file_uri}"

        config = self.set_graph_config(config=config)

        cypher_import = f'CALL n10s.rdf.import.fetch("{uri}", "Turtle")'
        response = self.query(cypher_import)[0]
        response["config"] = config
        return response

    def import_ttl_data(
            self,
            dataset: str,
            query: str = "DESCRIBE * WHERE {?s ?p ?o .} LIMIT 10",  # Get everything
            config: Union[ConfigOption, dict] = ConfigOption()
    ):
        """Import turtle data directly from the triplestore."""
        api_url = TS_HOST + dataset + "/query"

        config = self.set_graph_config(config=config)
        header_query_cypher = f'{{headerParams: {{ Accept: "application/turtle", Authorization: "Basic {TS_USER}:{TS_PWD}"}}, payload: "query={query}"}}'
        cypher_import = f'CALL n10s.rdf.import.fetch("{api_url}", "Turtle", {header_query_cypher})'
        resp = self.query(cypher_import)
        return resp
