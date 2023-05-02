"""API for interfacing with the Fuseki triple store. Requires Python 3.10+"""
import json
import uvicorn
import pathlib
import requests

from typing import Tuple, Union, Optional, Annotated
from datetime import timedelta

from jose import jwt, JWTError
from requests_toolbelt.multipart.encoder import MultipartEncoder
from rdflib import Graph

from SPARQLWrapper import TURTLE, XML, JSONLD, DESCRIBE, SELECT, ASK, CONSTRUCT

from fastapi import FastAPI, File, UploadFile, Path, Request, HTTPException, Depends, Form, BackgroundTasks, status, \
    Query
from fastapi.responses import JSONResponse, HTMLResponse, Response, FileResponse
from fastapi.security import OAuth2PasswordRequestForm
from fastapi.middleware.cors import CORSMiddleware


from neo4jconn import Neo4jConnection
from constants import TS_HOST, TS_PWD, TS_USER, NEO4J_HOST, NEO4J_DB_USER, NEO4J_UPLOAD_USER, NEO4J_UPLOAD_PWD, \
    NEO4J_URI, NEO4J_DB_PWD, ROOT_PATH, SECRET_KEY, SHARED
from models import ReturnFormat, MissingDatasetException, InvalidQueryTypeException, SparqlQuery, TripleCount, \
    ConfigOption
from utils import create_sparql_wrapper_for_triplestore, count_triples_in_dataset, get_datasets_in_triplestore, \
    query_and_convert, delete_file, generate_results
from csv_2_rdf import csv_to_ttl
from auth import Token, User, ACCESS_TOKEN_EXPIRE_MINUTES, authenticate_user, create_access_token, \
    get_password_hash, ALGORITHM, oauth2_scheme, TokenData, get_user

tags_metadata = [
    {
        "name": "Query",
        "description": "Methods for querying different datasets."
    },
    {
        "name": "Upload",
        "description": "Methods for uploading RDF data into triple store and Neo4J manually."
    },
    # {
    #     "name": "Config",
    #     "description": "Methods for getting and setting various configuration options."
    # },
    {
        "name": "Pipeline",
        "description": "Methods for importing directly via multiple repo e.g. from triplestore directly to Neo4j."
    },
    {
        "name": "Convert",
        "description": "Converts CSV files to RDF/TTL files for the triple store"
    }
]

app = FastAPI(
    root_path=ROOT_PATH,
    openapi_tags=tags_metadata,
    title="Wrapper API for the Apache Jena-Fuseki API",
    description="""This API uses the `requests` and `SPARQLWrapper` libraries to simplify querying and uploading 
    to the Jena-Fuseki server that is serving as our triple store.

* [Jena-Fuseki SPARQL over HTTP](https://jena.apache.org/documentation/fuseki2/soh.html)
* [Jena-Fuseki Admin Protocol](https://jena.apache.org/documentation/fuseki2/fuseki-server-protocol.html)
* [SPARQLWrapper documentation for Fuseki](https://sparqlwrapper.readthedocs.io/en/latest/main.html#fuseki)
"""
)

app.add_middleware(
    CORSMiddleware,
    allow_origins="*",
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
    expose_headers=["*"],
)

# Pseudo-database
TS_PWD_HASH = get_password_hash(TS_PWD)
DB = {
    TS_USER: {
        "username": TS_USER,
        "hashed_password": TS_PWD_HASH,
    }
}


# Security
@app.post("/token", response_model=Token)
async def login_for_access_token(
    form_data: Annotated[OAuth2PasswordRequestForm, Depends()]
):
    user = authenticate_user(DB, form_data.username, form_data.password)
    if not user:
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Incorrect username or password",
            headers={"WWW-Authenticate": "Bearer"},
        )
    access_token_expires = timedelta(minutes=ACCESS_TOKEN_EXPIRE_MINUTES)
    access_token = create_access_token(
        data={"sub": user.username}, expires_delta=access_token_expires
    )
    return {"access_token": access_token, "token_type": "bearer"}

async def get_current_user(token: Annotated[str, Depends(oauth2_scheme)]):
    credentials_exception = HTTPException(
        status_code=status.HTTP_401_UNAUTHORIZED,
        detail="Could not validate credentials",
        headers={"WWW-Authenticate": "Bearer"},
    )
    try:
        payload = jwt.decode(token, SECRET_KEY, algorithms=[ALGORITHM])
        username: str = payload.get("sub")
        if username is None:
            raise credentials_exception
        token_data = TokenData(username=username)
    except JWTError:
        raise credentials_exception
    user = get_user(DB, username=token_data.username)
    if user is None:
        raise credentials_exception
    return user


async def get_current_active_user(
    current_user: Annotated[User, Depends(get_current_user)]
):
    if current_user.disabled:
        raise HTTPException(status_code=400, detail="Inactive user")
    return current_user


# Exceptions
@app.exception_handler(InvalidQueryTypeException)
async def invalid_query_type_exception_handler(request: Request, exc: InvalidQueryTypeException):
    return JSONResponse(
        status_code=400,
        content={"message": "Return formats are restricted to specific query types: SELECT or ASK - xml, "
                            "json, csv, tsv; CONSTRUCT or DESCRIBE - ttl, xml, jsonld"},
    )


@app.exception_handler(MissingDatasetException)
async def missing_dataset_exception_handler(request: Request, exc: MissingDatasetException):
    return JSONResponse(
        status_code=404,
        content={"message": "Dataset not found in triple store."},
    )


# API Methods
@app.post(
    "/results/{dataset}/{result_format}",
    tags=["Query"],
    response_class=Response,
)
def query_triple_store(
        current_user: Annotated[User, Depends(get_current_active_user)],
        query: SparqlQuery,
        result_format: ReturnFormat,
        dataset: str = Path(
            description="The dataset in the triple store to query.",
            example="geonames"
        ),
):
    """Get the SPARQL query results from the triple store and return it as JSON."""
    sparql_wrapper = create_sparql_wrapper_for_triplestore(dataset=dataset)
    sparql_wrapper.setReturnFormat(result_format)
    sparql_wrapper.setQuery(query.query)

    results = query_and_convert(sw=sparql_wrapper)

    if sparql_wrapper.queryType in (CONSTRUCT, DESCRIBE):
        if result_format == TURTLE:
            g = Graph()
            g.parse(data=results, format=result_format)
            resp = HTMLResponse(g.serialize(format=result_format))

        elif result_format == JSONLD:
            converted = results.serialize(format=result_format)
            resp = JSONResponse(json.loads(converted))

        else:
            resp = HTMLResponse(results.serialize(format=result_format))

    elif sparql_wrapper.queryType in (SELECT, ASK):
        if result_format == XML:
            resp = HTMLResponse(results.toxml())

        else:
            resp = JSONResponse(results)

    else:
        resp = JSONResponse(status_code=403, content={"message": f"{sparql_wrapper.queryType} not allowed."}, )

    return resp


@app.get("/results/count/{dataset}", tags=["Query"])
def get_number_triples_in_dataset(
        dataset: str = Path(
            description="The dataset in the triple store to query.",
            example="fluid",
            # response_model=TripleCount,
        ),
) -> TripleCount:
    """Returns the number of triples in a given dataset as JSON."""
    triple_count = count_triples_in_dataset(dataset=dataset)
    return TripleCount(count=triple_count)


# @app.get("/config/neo4j/get/{database}", tags=["Config"], response_model=Neo4jImportResponse)
# def get_neo4j_configuration(
#         database: str = Path(
#             description="The name of the Neo4J database to check.",
#             example="fluid",
#         ),
# ):
#     """Get the current configuration of a Neo4J database."""
#     db = Neo4jConnection(uri=NEO4J_URI, user=NEO4J_DB_USER, pwd=NEO4J_DB_PWD, database=database)
#     resp = db.get_graph_config()
#     return resp
#
#
# @app.post("/config/neo4j/set/{database}", tags=["Config"])
# def set_neo4j_configuration(
#         config: ConfigOption,
#         database: str = Path(
#             description="The name of the Neo4J database to check.",
#             example="fluid",
#         ),
#         user=Depends(manager),
# ):
#     """Set the graph database configuration."""
#     db = Neo4jConnection(uri=NEO4J_URI, user=NEO4J_DB_USER, pwd=NEO4J_DB_PWD, database=database)
#     resp = db.set_graph_config(config=config)
#     return resp


def write_uploaded_file(uploaded_file: UploadFile):
    """Write the uploaded file to local storage."""
    try:
        with open(uploaded_file.filename, 'wb') as f:  # Create tmp file
            while contents := uploaded_file.file.read(1024 * 1024 * 500):  # Read in 500MB chunks
                f.write(contents)

    except Exception:
        raise HTTPException(status_code=400, detail="There was an error uploading the file")

    finally:
        uploaded_file.file.close()

    return uploaded_file.filename


def upload_data_to_triplestore(
        rdf_file: Union[UploadFile, str],
        tag: str,
        dataset: str = Form(...),
) -> Tuple[bool, str]:
    """Create a new dataset in the triplestore and upload data to it."""
    # Create dataset if not exists
    dataset_url, existing_datasets = get_datasets_in_triplestore()  # Check if dataset exists in store
    create_data = {"dbType": "tdb", "dbName": dataset}

    if dataset not in existing_datasets:
        create_resp = requests.post(dataset_url, auth=(TS_USER, TS_PWD), data=create_data)
        assert create_resp.ok

    # Upload data
    if tag == 'upload':
        local_file = write_uploaded_file(rdf_file)
    else:
        local_file = rdf_file

    upload_url = TS_HOST + dataset + "/data"
    tmp_f = open(local_file, 'rb')
    multipart_data = MultipartEncoder(
        fields={'file': (local_file, tmp_f, 'x-turtle')}
    )

    try:
        upload_resp = requests.put(
            upload_url,
            data=multipart_data,
            auth=(TS_USER, TS_PWD),
            headers={'Content-Type': multipart_data.content_type},
            # verify=False,
        )
        return upload_resp.ok, upload_resp.json()

    except Exception as e:
        raise HTTPException(status_code=400, detail=e)

    finally:  # Remove uploaded file
        if tag == "upload":
            tmp_f.close()
            pathlib.Path(local_file).unlink()


@app.put("/upload/ts/{dataset}", tags=["Upload"])
async def upload_data(
        current_user: Annotated[User, Depends(get_current_active_user)],
        dataset: str = Path(title="Dataset name"),
        rdf_file: UploadFile = File(...),
):
    try:
        success, resp = upload_data_to_triplestore(rdf_file=rdf_file, dataset=dataset, tag="upload")
        if success:
            return {"message": "Data upload successful", "response": resp}
        else:
            raise HTTPException(status_code=400, detail="Data upload failed")

    except Exception as e:
        raise HTTPException(status_code=400, detail=str(e))


@app.put("/upload/neo4j", tags=["Upload"])
def upload_file_neo4j(
        config: ConfigOption,
        rdf_file: UploadFile = File(..., description="RDF file to upload to the triple store"),
        database: str | None = Query(
            description="The name of the database to upload data to. If using the community version of Neo4J, this can "
                        "be left blank since there is only one database.",
            default=None,
        ),
        user=Depends(get_current_active_user),
):
    """Create a new database in the neo4j instance and upload data to it. This uses the uploader service implemented
    by Neo4J."""
    # Upload
    uploaded_file_name = write_uploaded_file(rdf_file)

    upload_url = f"{NEO4J_HOST}/upload/{database}/{uploaded_file_name}"

    if not SHARED:  # Neo4J hosted remotely
        try:
            upload_resp = requests.put(
                upload_url,
                data=open(uploaded_file_name, 'rb').read(),
                auth=(NEO4J_UPLOAD_USER, NEO4J_UPLOAD_PWD),
                headers={
                    'Content-type': 'application/x-turtle',
                },
            )
            if not upload_resp.ok:
                return upload_resp.text

        except Exception as e:
            raise HTTPException(status_code=400, detail=e)

        finally:  # Remove uploaded file
            pathlib.Path(uploaded_file_name).unlink()

        file_path = f"/neo4j/upload/{database}/{uploaded_file_name}"

    else:
        with open(uploaded_file_name, 'r') as upf:
            content = upf.read()

        pathlib.Path(uploaded_file_name).unlink()

        tmp_file = pathlib.Path(f"/upload/{uploaded_file_name}")
        with open(tmp_file, "w") as upf:
            upf.write(content)

        file_path = f"/import/{uploaded_file_name}"

    # Import
    db = Neo4jConnection(uri=NEO4J_URI, user=NEO4J_DB_USER, pwd=NEO4J_DB_PWD, database=database)
    import_resp = db.import_ttl_file(file_uri=file_path, config=config)

    if SHARED:
        tmp_file.unlink()

    return import_resp


@app.post("/convert", tags=["Convert"])
async def convert_csv_to_ttl(
        bg_task: BackgroundTasks,
        csv_file: UploadFile = File(..., description="Tabular data to convert to TTL."),
        json_file: UploadFile = File(..., description="JSON containing the ontology mappings for the CSV columns"),
        uri_file: UploadFile = File(..., description="Tabular data to map values to resources"),
        dataset: Optional[str] = Form(None, description="Name of the dataset in the triplestore to upload to."),
        output_file: str = "output.ttl",
        user=Depends(get_current_active_user),  # Requires authorization to import to triplestore
):
    """Convert CSV data to TTL format using a mapping file and upload to the triple store. If no dataset is given,
    then only the TTL file will be generated."""

    with open('input.csv', 'wb') as f:
        f.write(await csv_file.read())

    with open('mapping.json', 'wb') as f:
        f.write(await json_file.read())

    with open('uri_mapping.csv', 'wb') as f:
        f.write(await uri_file.read())

    output_format = output_file.split(".")[-1]

    if output_format not in ["ttl", "nt", "xml", "json-ld"]:
        return {"error": "Unsupported file format"}

    conversion_results = csv_to_ttl('input.csv', 'mapping.json', 'uri_mapping.csv',output_file, output_format=output_format)

    f.close()
    pathlib.Path('input.csv').unlink()
    pathlib.Path('mapping.json').unlink()
    pathlib.Path('uri_mapping.csv').unlink()

    if dataset:
        try:
            uploader = upload_data_to_triplestore(rdf_file= output_file, dataset= dataset, tag="converter")
            upload_message = uploader[1]  # success message

        except Exception as e:
            upload_message = str(e)  # error message

        bg_task.add_task(delete_file, output_file)  # remove output file after returning

    else:
        upload_message = conversion_results if isinstance(conversion_results, str) else "file successfully generated"

    return FileResponse(
        output_file,
        media_type='text/turtle',
        filename=output_file,
        headers={"message": f"{upload_message}"}
    )


@app.put("/import/{dataset}", tags=["Pipeline"])
def import_data_from_triplestore_to_neo4j(
        current_user: Annotated[User, Depends(get_current_active_user)],
        bg_task: BackgroundTasks,
        query: SparqlQuery,
        dataset: str = Path(
            description="The dataset in the triplestore to extract data from.",
            example="fluid",
        ),
        database: str | None = Query(
            description="The name of the database to upload data to. If using the community version of Neo4J, this can "
                        "be left blank since there is only one database.",
            default=None,
        ),
):
    """Here the user can specify the dataset to query in the triplestore and have the results automatically uploaded
    into a new database in Neo4J."""
    _, existing_datasets = get_datasets_in_triplestore()
    if dataset not in existing_datasets:
        raise HTTPException(status_code=404, detail="Dataset does not exist in the triplestore")

    count_result = count_triples_in_dataset(dataset=dataset)

    results = generate_results(dataset=dataset, count=count_result)

    # # Import
    # file_path = f"/neo4j/upload/{database}/{uploaded_file_name}"
    # db = Neo4jConnection(uri=NEO4J_URI, user=NEO4J_DB_USER, pwd=NEO4J_DB_PWD, database=database)
    # import_resp = db.import_ttl_file(file_uri=file_path, config=config)

    return HTMLResponse(results)


if __name__ == "__main__":
    uvicorn.run('ts_api:app', reload=True, port=8000, host="0.0.0.0", timeout_keep_alive=600)
