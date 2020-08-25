[![Build Status](https://travis-ci.org/alokkusingh/kafka-experimental.svg?branch=master)](https://travis-ci.org/github/alokkusingh/kafka-experimental)
[![GitHub issues](https://img.shields.io/github/issues/alokkusingh/kafka-experimental.svg)](https://github.com/alokkusingh/kafka-experimental/issues)
[![GitHub issues closed](https://img.shields.io/github/issues-closed-raw/alokkusingh/kafka-experimental.svg?maxAge=2592000)](https://github.com/alokkusingh/kafka-experimental/issues?q=is%3Aissue+is%3Aclosed)

# kafka-experimental
Kafka Experimental using Spring Kafka

Table of Contents
=================

   * [Current Deployment](#current-deployment)
   * [TO-DO](#to-do)
   * [Deployment](#deployment)

## Current Deployment
- 1 Zookeeper Instance
- 1 Kafka Broker
- 2 Producers
    - 1 instance of Rain Sensor
    - 1 instance of Temperature Sensor
- 1 Partition
- 2 Apps
    - `app-one` - subscribed to Rain Sensor and Temperature Sensor Topics. Consumer group: `app-one`
    - `app-two` - subscribed to Rain Sensor Topic. Consumer group: `app-two`

## TO-DO
- Add `Docker Swarm` Deployment Steps
- Add Multiple `Kafka Broker`
- Add Multiple `Partition`
- Add Multiple `Instances` of Each Apps
- Add `Apache Avro` Schema Registry

## Deployment

````
mvn clean package -DskipTests
````

````
docker-compose -d -f docker-compose.yml up --build
````

````
docker-compose stop
````
