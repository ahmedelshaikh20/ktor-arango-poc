# Coding Task for Ritec

Coding Assigment was about creating hospital system with ktor and Arangodb 
All relations between entities should be using graphs only

## Setup

Run the application file and now the server is listening on port 8080

## Api EndPoints For Patients 

1-Post a request to add NEW Patient.

    -Request Endpoint: patient/addPatient.
    -HTTP Verb:POST.
    -Request Content-Type: application/json.

```json

Request Body Exmaple for Patient
{
    "name" : "ahmed",
    "gender":"Non Binary"
}
```
2-Get request to get all Patients.

    -Request Endpoint: /patient.
    -HTTP Verb:GET.
    -Response Content-Type: application/json.

-Response Body Exmaple for all Patients
```json

{
    "data": [
        {
            "name": "Adham",
            "gender": "Male",
            "id": "33846",
            "rev": "_hx6cOBu---"
        },
        {
            "name": "ahmed",
            "gender": "Male",
            "id": "53287",
            "rev": "_hyLDjGi---"
        }
    ],
    "message": "Success"
}
```

3-Post Request to Register Hospital to User
    
    -Request Endpoint: /patient/registerHospital.
    -HTTP Verb:POST.
    -Response Content-Type: application/json.

```json
Request Body for Registeration
{
    "patientId":"33846",
    "hospital" : {
    "id":"33821",
    "name" : "El Gawy",
    "type":"private",
    "address":"newCairo"}
}

Response Body
{
    "data": {
        "nodeId": "53287",
        "target": "33821",
        "id": null,
        "rev": null,
        "from": "patient/53287",
        "to": "hospital/33821"
    },
    "message": "Success"
}
```
4-Get request to get all hospitals registered by specific user.

    -Request Endpoint: /patient/allHospitals/{id}.
    -HTTP Verb:GET.
    -Response Content-Type: application/json.

```json

Response Body Exmaple for all Hospitals
{
    "data": [
        {
            "name": "loool",
            "type": "private",
            "address": "newCairo",
            "id": "33821",
            "rev": "_hx7qcqa---"
        },
        {
            "name": "loool",
            "type": "private",
            "address": "newCairo",
            "id": "33821",
            "rev": "_hx7qcqa---"
        }
    ],
    "message": "Success"
}
```

5-Post Request to Delete User
```json

    -Request Endpoint: /patient/deletePatient/{id}.
    -HTTP Verb:GET.
    -Response Content-Type: application/json.

```
