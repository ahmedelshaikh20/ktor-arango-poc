package com.example.client.hospitalservice

import com.arangodb.ArangoDB
import com.arangodb.entity.BaseDocument
import com.example.data.model.Hospital
import com.example.data.model.Patient
import com.example.data.model.PatientHospitalRelation
import com.example.database.Db


class HospitalServiceImpl(val arangoDB: Db) : HospitalService {
  override fun getAllHospitals(): List<Hospital> {
    return arangoDB.list(klass = Hospital::class, query = "FOR h IN hospital RETURN h")
  }

  override fun createHospital(hospital: Hospital) {
    arangoDB.insert(hospital)
  }

  override fun modifyHospital(hospital: Hospital) {
    arangoDB.update(hospital)
  }

  override fun deleteHospital(hospitalId: String) {
    val hospital = arangoDB.one(
      Hospital::class,
      query = "FOR p IN hospital FILTER p._key LIKE ${hospitalId} RETURN p"
    )
    if (hospital != null)
      arangoDB.delete(hospital)
  }

  override fun getAllHospitalPatient(hospitalID: String): List<Patient> {
    val res = arangoDB.list(
      PatientHospitalRelation::class,
      query = "FOR h IN patienthospitalrelation FILTER h._to LIKE ${hospitalID}  RETURN h"
    )
    println(res)
    return res.map {
      arangoDB.one(
        Patient::class,
        query = "FOR p IN patient FILTER p._key LIKE ${it.from} RETURN p"
      )!!

    }
  }

}
