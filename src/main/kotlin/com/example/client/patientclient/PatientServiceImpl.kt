package com.example.client.patientclient

import com.example.data.model.Hospital
import com.example.data.model.Patient
import com.example.data.model.PatientHospitalRelation
import com.example.data.register.HospitalRegistrationBody
import com.example.database.Db
import com.example.utils.BaseResponse
import java.util.*

class PatientServiceImpl(val arangoDB: Db) : PatientService {
  override suspend fun getAllPatients(): BaseResponse<Any> {
    val patients = arangoDB.list(Patient::class, query = "FOR p IN patient RETURN p")
    return BaseResponse.SuccessResponse(data = patients, message = "Success")

  }

  override suspend fun addPatient(patient: Patient): BaseResponse<Any> {
    arangoDB.insert(patient)
    return BaseResponse.SuccessResponse(message = "Success")
  }

  override suspend fun deletePatient(patientID: String): BaseResponse<Any> {
    val patient = arangoDB.one(
      Patient::class,
      query = "FOR p IN hospital FILTER p._key LIKE ${patientID} RETURN p"
    )
    if (patient != null) {
      arangoDB.delete(patient)
      return BaseResponse.SuccessResponse(message = "Success")
    }
    return BaseResponse.ErrorResponse(message = "Cannot find Patient to delete")
  }

  override suspend fun modifyPatient(patient: Patient): BaseResponse<Any> {
    arangoDB.update(patient)
    return BaseResponse.SuccessResponse(message = "Success")

  }

  override suspend fun registerHospital(hospitalRegistrationBody: HospitalRegistrationBody): BaseResponse<Any> {
    val patient = arangoDB.one(
      Patient::class,
      query = "FOR p IN patient FILTER p._key LIKE ${hospitalRegistrationBody.patientId} RETURN p"
    )
    val hospital = arangoDB.one(
      Hospital::class,
      query = "FOR p IN hospital FILTER p._key LIKE ${hospitalRegistrationBody.hospital.id} RETURN p"
    )
    if (hospital != null && patient != null) {
      val patientHospitalRelation = PatientHospitalRelation(nodeId = patient.id, target = hospital.id).apply {
        from = "${Patient::class.simpleName?.lowercase(Locale.getDefault())}/${patient?.id}"
        to = "${Hospital::class.simpleName?.lowercase(Locale.getDefault())}/${hospital?.id}"
      }
      arangoDB.insert(patientHospitalRelation)
      return BaseResponse.SuccessResponse(data = patientHospitalRelation,message = "Success")
    }
    return BaseResponse.ErrorResponse(message = "One of the fields are null")

  }

  override suspend fun getAllRegisteredHospitals(patientId: String): BaseResponse<Any> {
    println(patientId)
    val res = arangoDB.list(
      PatientHospitalRelation::class,
      query = "FOR h IN patienthospitalrelation FILTER h.nodeId LIKE $patientId  RETURN h"
    )
    val hospitals = mutableListOf<Hospital?>()
    res.forEach {
      hospitals.add(
        arangoDB.one(
          Hospital::class,
          query = "FOR p IN hospital FILTER p._key LIKE ${it.target} RETURN p"
        )
      )

    }
    println(hospitals.toString())

    return BaseResponse.SuccessResponse(data = hospitals, message = "Success")
  }

}
