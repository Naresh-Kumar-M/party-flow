# Party Creation

## Introduction
## System Diagram 
![alt text](https://github.com/nareshm87/party-flow/blob/master/images/system.png?raw=true)
## Interaction diagram 
![alt text](https://github.com/nareshm87/party-flow/blob/master/images/sequence.png?raw=true)
## How to deploy 
- Download Kafka and run kafka on port 9092 (follow step 1 and 2 in https://kafka.apache.org/quickstart)
- Create kafka topics (follow step 3 in https://kafka.apache.org/quickstart)
```sh
$ $ bin/kafka-topics.sh --create --topic party-validation-request --bootstrap-server localhost:9092
$ $ bin/kafka-topics.sh --create --topic party-validation-response --bootstrap-server localhost:9092
$ $ bin/kafka-topics.sh --create --topic party-notification --bootstrap-server localhost:9092
```
- Start Party, Validation and Notification service by running following command on the respective project directories
```sh
$ mvn spring-boot:run
```
## How to use 
