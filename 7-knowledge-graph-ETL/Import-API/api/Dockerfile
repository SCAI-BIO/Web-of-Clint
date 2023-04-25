FROM python:3.10

RUN apt-get update && apt upgrade -y && apt install nano

ENV TS_HOST "localhost:3030"
ENV TS_USER "admin"
ENV TS_PWD "pw123"

ENV NEO4J_HOST "localhost:7687"
ENV NEO4J_DB_USER "neo4j"
ENV NEO4J_DB_PWD "neo4j"

ENV NEO4J_UPLOAD_USER "admin"
ENV NEO4J_UPLOAD_PWD "pw123"

WORKDIR api/

COPY requirements.txt .
RUN pip install -r ./requirements.txt

COPY . .

EXPOSE 8000

ENTRYPOINT ["python", "ts_api.py"]