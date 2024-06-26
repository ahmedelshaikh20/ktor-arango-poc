# KTOR with Arangodb Simple Assigment

Coding Assignment was about creating a hospital system with Ktor and Arangodb 
All relations between entities should be using graphs only

## Setup
1-Setup your arangodb with docker(if you are using Macos) then run the container and now the DB will have default port 8529.

2-Run the application file and now the server is listening on port 8080.

## Api EndPoints For Patients 

1-Post a request to add a NEW Patient.

    -Request Endpoint: patient/addPatient.
    -HTTP Verb: POST.
    -Request Content-Type: application/JSON.

- **Request body for Patient:**

```json

{
    "name" : "ahmed",
    "gender":"Male"
}
```
2-Get request to get all Patients.

    -Request Endpoint: /patient.
    -HTTP Verb:GET.
    -Response Content-Type: application/json.

- **Response Body Exmaple for all Patients:**
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

3-Post Request to Register Hospital to Patient
    
    -Request Endpoint: /patient/registerHospital.
    -HTTP Verb:POST.
    -Response Content-Type: application/json.

- **Request Body for Registeration:**

```json
{
    "patientId":"53287",
    "hospitalId":"33821"
}
```
- **Response Body:**
```json
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
- **Relation Visualization:**
![Relation](imgs/Relation.png)


4-Get request to get all hospitals registered by specific user.

    -Request Endpoint: /patient/allHospitals/{id}.
    -HTTP Verb:GET.
    -Response Content-Type: application/json.


- **Response Body Exmaple for all Hospitals:**
```json

{
    "data": [
        {
            "name": "Alex",
            "type": "private",
            "address": "newCairo",
            "id": "33821",
            "rev": "_hx7qcqa---"
        },
        {
            "name": "Elgawy",
            "type": "private",
            "address": "newCairo",
            "id": "33821",
            "rev": "_hx7qcqa---"
        }
    ],
    "message": "Success"
}
```

5-Post Request to Delete Patient

    -Request Endpoint: /patient/deletePatient/{id}.
    -HTTP Verb:GET.
    -Response Content-Type: application/json.



## Api EndPoints For Hospitals

1-Post a request to add NEW Hospital.

    -Request Endpoint: hospital/createHospital.
    -HTTP Verb:POST.
    -Request Content-Type: application/json.

- **Request body for Patient:**
```json

 {
    "name" : "Cairo Hospital",
    "type":"private",
    "address":"newCairo"
}
```

2-Get request to get all Hospitals.

    -Request Endpoint: /hospital.
    -HTTP Verb:GET.
    -Response Content-Type: application/json.

- **Response Body Exmaple for all Hospitals:**
```json

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
            "name": "Cairo Hospital",
            "type": "private",
            "address": "newCairo",
            "id": "54644",
            "rev": "_hyLwDZO---"
        }
    ],
    "message": "Success"
}
```
3-Get request to get all patients registered in specific hospital.

    -Request Endpoint: /hospital/allPatients/{id}.
    -HTTP Verb:GET.
    -Response Content-Type: application/json.
    
- **Response Body Exmaple for all Patients:**
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
5-Post Request to Delete Hospital

    -Request Endpoint: /hospital/deleteHospital/{id}.
    -HTTP Verb:GET.
    -Response Content-Type: application/json.


    
