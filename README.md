[![Build Status](https://travis-ci.org/alokkusingh/kafka-experimental.svg?branch=master)](https://travis-ci.org/github/alokkusingh/kafka-experimental)
[![GitHub issues](https://img.shields.io/github/issues/alokkusingh/kafka-experimental.svg)](https://github.com/alokkusingh/kafka-experimental/issues)
[![GitHub issues closed](https://img.shields.io/github/issues-closed-raw/alokkusingh/kafka-experimental.svg?maxAge=2592000)](https://github.com/alokkusingh/kafka-experimental/issues?q=is%3Aissue+is%3Aclosed)

# kafka-experimental
Kafka Experimental using Spring Kafka and Apache Avro.

Using `Apache Avro` for model generation for Producer and Consumer. 

Using my own `Avro Serializer` utility library [https://github.com/alokkusingh/avro-serializer/packages/373458](https://github.com/alokkusingh/avro-serializer/packages/373458) to Serialize/Deserialize Avro model objects without using `Schema Registry`.

Table of Contents
=================

   * [Current Deployment](#current-deployment)
   * [TO-DO](#to-do)
   * [Deployment](#deployment)

## Current Deployment 
- 1 Zookeeper Instance
- 1 Kafka Broker
- 2 Producers - you may scale up using `docker-compose` scale command
    - 1 instance of Rain Sensor 
    - 1 instance of Temperature Sensor - you may scale up using `docker-compose` scale command
- 1 Partition
- 2 Apps (`consumer`s from 2 diffrenet `consumer group`) - you may scale up using `docker-compose` scale command. This will be useless uless we have multiple `Partition`s.
    - `app-one` - subscribed to Rain Sensor and Temperature Sensor Topics. Consumer group: `app-one`
    - `app-two` - subscribed to Rain Sensor Topic. Consumer group: `app-two`
- Apache Avro used as schema definition to serialize and deserialize the message. Avro schema definition is maintained locally, no schema registry was used.

## TO-DO
- Add `Docker Swarm` Deployment Steps
- Add Multiple `Kafka Broker`
- Add Multiple `Partition`
- Add Multiple `Instances` of Each Apps
- Add `Apache Avro` Schema Registry - currently Avro is used only for object mapping. Without schema registry schema versioning won't be possible

## Few Notes
- Producer creates topic on startup using "NewTopic" by providing "Number of Partitions" and "Replication Factor"

## Deployment

### Build the Maven Artifact
````
mvn clean package
````

### Deploy the docker services
````
docker-compose -d -f docker-compose.yml up --build
````

### Stop the docker services
````
docker-compose stop
````

### Scale up/down/stop one or more docker service
````
docker-compose -f docker-compose.yml scale app-two=0
````
