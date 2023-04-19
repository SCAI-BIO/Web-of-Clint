import os

K8S = os.getenv('K8S')

# Root path
ROOT_PATH = "/api" if K8S else ""

# Credentials and paths
TS_HOST = "http://scaiview-fuseki-who-backup-service:3030/" if K8S else "https://triplestore.scaiview.com/"
TS_USER = os.getenv("TS_USER")
TS_PWD = os.getenv("TS_PWD")

NEO4J_HOST = "https://dev-kg-who-ewaa.graphapp.io"
NEO4J_URI = "neo4j+s://dev-kg-who-ewaa.graphapp.io:7687"
NEO4J_DB_USER = os.getenv("NEO4J_DB_USER")
NEO4J_DB_PWD = os.getenv("NEO4J_DB_PWD")
NEO4J_UPLOAD_USER = os.getenv("NEO4J_UPLOADER_USER")
NEO4J_UPLOAD_PWD = os.getenv("NEO4J_UPLOADER_PWD")

# Security
SECRET_KEY = "ecebbbce8bc8d45c3ecd92cfd497458e95670f74a9e651ff"
