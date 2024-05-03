package com.example.client.patientclient

import com.example.client.hospitalservice.HospitalService
import com.example.data.model.Hospital
import com.example.data.model.Patient
import com.example.data.model.PatientHospitalRelation
import com.example.data.register.HospitalRegistrationBody
import com.example.database.Db
import java.util.*

class PatientServiceImpl(val arangoDB: Db) : PatientService {
  override suspend fun getAllPatients(): List<Patient> {
    return arangoDB.list(Patient::class, query = "FOR p IN patient RETURN p")
  }

  override suspend fun addPatient(patient: Patient) {
    arangoDB.insert(patient)
  }

  override suspend fun deletePatient(patientID: String) {
    val patient = arangoDB.one(
      Patient::class,
      query = "FOR p IN hospital FILTER p._key LIKE ${patientID} RETURN p"
    )
    if (patient != null)
      arangoDB.delete(patient)  }

  override suspend fun modifyPatient(patient: Patient) {
    arangoDB.update(patient)
  }

  override suspend fun registerHospital(hospitalRegistrationBody: HospitalRegistrationBody) {
    println(hospitalRegistrationBody.patientId)
    val patient = arangoDB.one(
      Patient::class,
      query = "FOR p IN patient FILTER p._key LIKE ${hospitalRegistrationBody.patientId} RETURN p"
    )
    val hospital = arangoDB.one(
      Hospital::class,
      query = "FOR p IN hospital FILTER p._key LIKE ${hospitalRegistrationBody.hospital.id} RETURN p"
    )
    val patientHospitalRelation = PatientHospitalRelation().apply {
      from = "${Patient::class.simpleName?.lowercase(Locale.getDefault())}/${patient?.id}"
      to = "${Hospital::class.simpleName?.lowercase(Locale.getDefault())}/${hospital?.id}"
    }
    println(patientHospitalRelation.from)

    arangoDB.insert(patientHospitalRelation)
  }

  override suspend fun getAllRegisteredHospitals(patientId: String): List<Hospital> {
    println(patientId)
    val res = arangoDB.list(
      PatientHospitalRelation::class,
      query = "FOR h IN patienthospitalrelation FILTER h._from LIKE $patientId  RETURN h"
    )
    return res.map {
      arangoDB.one(
        Hospital::class,
        query = "FOR p IN hospital FILTER p._key LIKE ${it.to} RETURN p"
      )!!

    }
  }

}
