# kafka-experimental
Kafka Experimental using Spring Kafka

Table of Contents
=================

   * [Current Deployments](#current-deployments)
   * [TO-DO](#to-do)
   * [Deployment](#deployment)

## Current Deployments
- 1 Zookeeper Instance
- 1 Kafka Broker
- 2 Producers
    - 1 instance of Rain Sensor
    - 1 instance of Temperature Sensor
- 1 Partition
- 2 Apps
    - `app-one` - listen to Rain Sensor and Temperature Topics. Consumer group: `app-one`
    - `app-two` - listen ro Rain Sensor Topic. Consumer group: `app-two`

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
