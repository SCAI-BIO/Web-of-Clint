FROM python:3.10

RUN apt-get update && apt upgrade -y && apt install nano

WORKDIR api/

COPY .. .
RUN pip install -r ./requirements.txt

ENTRYPOINT ["python", "ts_api.py"]