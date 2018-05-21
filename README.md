# Appliance control service

## Description

This is a small implementation of appliance control backend.

## Used technologies

```
1. Java 8
2. Spring Boot 2.0.2
3. JDBC on DAO layer
4. H2 as in-memory storage
5. MockMvc, Mockito for testing

```

## Prerequisites

```
1. Maven 3.x.x
2. Java 8

```

## Installation

```
1. mvn clean package
2. cd target
3. java -jar appliance-control-0.0.1-SNAPSHOT.jar
4. curl http://localhost:8080/actuator/health
   should respond with {"status":"UP"}
```

## API methods

###1. POST /appliance (register an appliance)

Description:

Register newly installed appliance

Request:
```
{"type":"WASH_MACHINE"}
```

Response:
```
{"applianceId":<SOME_UUID_HERE>}
```

### 2. POST /appliance/{id}/command

Create new command to appliance. This operation is invoked by user

Request:
```
{"command":"WASH"}
```

Response:
HTTP CODE 200

### 3. GET /appliance/{id}/command

Retrieve command for appliance with given identifier.
This operation is invoked by appliance knowing own id.
After fetching the command it is deleted from the db.

Request:
```
EMPTY
```

Response:
```
{"command":"WASH"}
```

### 4. GET /appliance/{id}/status

Retrieve status of the appliance.
In case of retrieval status of non-existent appliance
an error is thrown.

Request:
```
EMPTY
```

Response:
```
{"status":"READY"}
```

### 5. PUT /appliance/{id}/status

Update status of the appliance.
In case of updating status of non-existent appliance
an error is thrown.

Request:
```
{"status":"READY"}
```

Response:
HTTP CODE 200


## Author

Valery Yakovlev
