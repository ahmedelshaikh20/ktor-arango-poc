package com.example.plugins

import com.example.client.hospitalservice.HospitalService
import com.example.client.patientclient.PatientService
import com.example.client.patientclient.PatientServiceImpl
import com.example.data.model.Hospital
import com.example.data.model.Patient
import com.example.data.register.HospitalRegistrationBody
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureRouting(hospitalService: HospitalService, patientService: PatientService) {
  routing {
    route("/hospital") {
      get("/") {
        try {
          val hospitals = hospitalService.getAllHospitals()
          call.respond(hospitals)

        } catch (e: Exception) {
          call.respond(HttpStatusCode.BadRequest, message = e.message.toString())
        }
      }
      post("/createHospital") {
        try {
          val hospital = call.receive<Hospital>()
          val response = hospitalService.createHospital(hospital)
          call.respond(response)

        } catch (e: Exception) {
          call.respond(HttpStatusCode.BadRequest, message = e.message.toString())
        }
      }
      post("/modifyHospital") {
        try {
          val hospital = call.receive<Hospital>()
          val response = hospitalService.modifyHospital((hospital))
          call.respond(response)

        } catch (e: Exception) {
          call.respond(HttpStatusCode.BadRequest, message = e.message.toString())
        }
      }
      post("/deleteHospital/{id}") {
        try {
          val id = call.parameters["id"].toString()
          println(id)
          hospitalService.deleteHospital((id))
          call.respond(HttpStatusCode.OK, message = "Success")
        } catch (e: Exception) {
          call.respond(HttpStatusCode.BadRequest, message = e.message.toString())
        }
      }
      get("/allPatients/{id}") {
        try {
          val id = call.parameters["id"]
          println(id)
          if (id != null) {
            val hospitals = hospitalService.getAllHospitalPatient(id)
            call.respond(HttpStatusCode.OK, hospitals)
          }
        } catch (e: Exception) {
          call.respond(HttpStatusCode.BadRequest, message = e.message.toString())
        }
      }

    }
    /////////////////////////////////////////////////////////////


    route("/patient") {
      get("/") {
        try {
          val patients = patientService.getAllPatients()
          call.respond(patients)
        } catch (e: Exception) {
          call.respond(HttpStatusCode.BadRequest, message = e.message.toString())
        }
      }
      post("/addPatient") {
        try {
          val patient = call.receive<Patient>()
          patientService.addPatient(patient)
        } catch (e: Exception) {
          call.respond(HttpStatusCode.BadRequest, message = e.message.toString())
        }
      }
      post("/modifyPatient") {
        try {
          val patient = call.receive<Patient>()
          val response = patientService.modifyPatient((patient))
          call.respond(response)
        } catch (e: Exception) {
          call.respond(HttpStatusCode.BadRequest, message = e.message.toString())
        }
      }
      post("/registerHospital") {
        try {
          val body = call.receive<HospitalRegistrationBody>()
          val response = patientService.registerHospital((body))
          call.respond(response)

        } catch (e: Exception) {
          call.respond(HttpStatusCode.BadRequest, message = e.message.toString())
        }
      }
      get("/allHospitals/{id}") {
        try {
          val id = call.parameters["id"]
          println(id)
          if (id != null) {
            val hospitals = patientService.getAllRegisteredHospitals(id)
            call.respond(HttpStatusCode.OK, hospitals)
          }
        } catch (e: Exception) {
          call.respond(HttpStatusCode.BadRequest, message = e.message.toString())
        }
      }

    }


  }

}
