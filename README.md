# kafka-experimental
Kaka Experimental


````
docker stack deploy --with-registry-auth --compose-file=docker-compose.yml kafka-experimental
````

````
docker service ls
````

````
docker stack ps kafka-experimental
````

````
docker service logs kafka-experimental_zookeper
docker service logs kafka-experimental_kafka
````


````
docker stack rm kafka-experimental
````

````
docker service update --force kafka-experimental_kafka
````