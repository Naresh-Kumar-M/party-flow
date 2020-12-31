# Party Flow

## Introduction
 The party flow system consists of 3 services namely 
 - Party service 
 - Validation service 
 - Notification service
 
 Party service is responsible for exposing REST endpoints for party creation request and approval. It is also responsible for lifecycle of party request. Party service interacts with other services in the system asynchronously through Kafka. Essentialy, it generates validation event for address validation and notification event to trigger email notification to indicate success/failure of party creation. It uses in memory h2 database to store party requests and parties.
 
 Validation service is responsible for consuming the events from kafka and validate the address by connecting to 3rd party service(address doctor) and generate the address validation response event indicating the validation status.(Note :  this service acts as a stub responding with true for all requests) This service can be enhanced to used resttemplate to invoke the REST APIs exposed by address doctor.

Notification service is responsible for sending email notification to the parties created. 
 
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
- Start Party, Validation and Notification service by running following command on the respective project directories. (Note : for notification service, change the values for email id and password in applicationproperties.yaml https://github.com/nareshm87/party-flow/blob/master/notification/src/main/resources/application.yaml)
```sh
$ mvn spring-boot:run
```
## How to use 
- create party request 
 ```sh
   POST http://localhost:8444/party/
   {
    "firstName" : "Naresh",
    "lastName" : "Kumar",
    "address" : " SLR",
    "email" : "naresh@gmail.com",
    "contactNumber" : "973998696"
   }
   ```
- list all party requests
```sh
  GET http://localhost:8444/party/requests
  ```
- approve party creation request
 
```sh
  POST http://localhost:8444/party/approval/{requestId}/success
  ```
   requestId - request id returned upon create party request
   - deny party creation request
   ```sh
  POST http://localhost:8444/party/approval/{requestId}/fail
  ```
  requestId - request id returned upon create party request
- list all parties 
```sh
   GET http://localhost:8444/party/
 ```
