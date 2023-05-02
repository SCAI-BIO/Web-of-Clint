import os
import re


def format_url(url):
    if not re.match('(?:http|ftp|https)://', url):
        return 'http://{}'.format(url)
    return url


K8S = os.getenv('K8S')
SHARED = os.getenv("SHARED") or True  # Default to True if None given

# Root path
ROOT_PATH = "/api" if K8S else ""

# Credentials and paths
TS_HOST = format_url(os.getenv("TS_HOST")) + "/"  # URL to triplestore
TS_USER = os.getenv("TS_USER")  # Triplestore username
TS_PWD = os.getenv("TS_PWD")  # Triplestore password

NEO4J_HOST_ROOT = os.getenv("NEO4J_HOST")  # URL to Neo4J instance
NEO4J_HOST = NEO4J_HOST_ROOT if NEO4J_HOST_ROOT.startswith("https://") else "https://" + NEO4J_HOST_ROOT

if SHARED:
    NEO4J_URI = "neo4j://" + NEO4J_HOST_ROOT
else:
    NEO4J_URI = "neo4j+s://" + format_url(NEO4J_HOST_ROOT).rstrip(":7687") + ":7687"

NEO4J_DB_USER = os.getenv("NEO4J_DB_USER")  # Username to Neo4J database
NEO4J_DB_PWD = os.getenv("NEO4J_DB_PWD")  # Password to Neo4J database
NEO4J_UPLOAD_USER = os.getenv("NEO4J_UPLOADER_USER") or NEO4J_DB_USER  # Username for uploading data
NEO4J_UPLOAD_PWD = os.getenv("NEO4J_UPLOADER_PWD") or NEO4J_DB_PWD  # Password for uploading data

# Security
SECRET_KEY = "ecebbbce8bc8d45c3ecd92cfd497458e95670f74a9e651ff"
