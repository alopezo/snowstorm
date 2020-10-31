# Using Docker

## Pre-requisites

A somewhat obvious point, but please make sure you have installed Docker. Between the two containers, Elasticsearch and Snowstorm, 8Gb memory is used, so make sure that your installation of docker has the necessary memory allocated.

## Starting Snowstorm

From the project directory run:

```bash
docker-compose up -d
```

This uses the [`docker-compose.yml`](../docker-compose.yml) file and will start Snowstorm and Elasticsearch in separate containers without the need to build anything. However, **you will need to load a SNOMED CT release into Snowstorm**.


## Loading SNOMED CT

To get SNOMED CT into your new docker environment, youi will need load a SNOMED CT release directly into the running docker container using the [loading SNOMED instructions](loading-snomed.md).

The [`docker-compose.yml`](../docker-compose.yml) creates a docker volume that will be re-used when the containers are rebuilt, so your data will not be lost unless the volume is deleted.

## Running in Read-Only mode

Once the data is loaded Snowstorm can be run in read-only mode, as documented elsewhere. In order to run your docker containers in a read-only mode, please make the following change to the relevant line in the [`docker-compose.yml`](../docker-compose.yml) file:

```bash
entrypoint: java -Xms2g -Xmx4g -jar snowstorm.jar --elasticsearch.urls=http://es:9200 --snowstorm.rest-api.readonly=true
```

Other config options may be of interest when running you own instance, for example `snowstorm.rest-api.readonly.allowReadOnlyPostEndpoints` and `snowstorm.rest-api.allowUnlimitedConceptPagination` which default to false. See the [Configuration Guide](configuration-guide.md).
