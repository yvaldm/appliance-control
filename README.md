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

### 1. POST /appliance (register an appliance)

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

## Example (using curl)


### 1. Register appliance

```
curl 'http://localhost:8080/appliance' -H 'content-type: application/json' --data-binary $'{ "type": "WASH_MACHINE"}' 
```

Response: 
```
{"applianceId":"86079278-5062-4135-a398-16d29a152082"}% 
```

### 2. Get initial status of appliance

```
curl http://localhost:8080/appliance/86079278-5062-4135-a398-16d29a152082/status 
```

Response:
```
{"status":"READY"}
```

### 3. Send command (User)

```
curl 'http://localhost:8080/appliance/86079278-5062-4135-a398-16d29a152082/command' -H 'content-type: application/json' --data-binary $'{ "command": "WASH"}'
```

Response: 200 


### 4. Fetch command (Appliance)

```
curl 'http://localhost:8080/appliance/86079278-5062-4135-a398-16d29a152082/command' 
```

Response:
```
{"command":"WASH"}
```

### 5. Fetch status of the appliance calculated by state machine

```
curl 'http://localhost:8080/appliance/86079278-5062-4135-a398-16d29a152082/status'  
```

Response:
```
{"status":"WASHING"}
```

### 6. Update status to ready when wash is finished

```
curl -XPUT 'http://localhost:8080/appliance/73d1d66c-7015-4e15-86b2-0b07d95c6e4c/status' -H 'content-type: application/json' --data-binary $'{ "status": "READY"}' 
```

Response: 200

### 7. Get status of the appliance after status update

```
curl 'http://localhost:8080/appliance/73d1d66c-7015-4e15-86b2-0b07d95c6e4c/status'                                                                           
```

Response:
```
{"status":"READY"} 

```

## Author

Valery Yakovlev
