package com.example.client.hospitalservice

import com.arangodb.ArangoDB
import com.arangodb.entity.BaseDocument
import com.example.data.model.Hospital
import com.example.data.model.Patient
import com.example.data.model.PatientHospitalRelation
import com.example.database.Db
import com.example.utils.BaseResponse


class HospitalServiceImpl(val arangoDB: Db) : HospitalService {
  override fun getAllHospitals(): BaseResponse<Any> {
    val hospitals = arangoDB.list(klass = Hospital::class, query = "FOR h IN hospital RETURN h")
    return BaseResponse.SuccessResponse(data = hospitals, message = "Success")
  }

  override fun createHospital(hospital: Hospital): BaseResponse<Any> {
    arangoDB.insert(hospital)
    return BaseResponse.SuccessResponse(message = "Success")

  }

  override fun modifyHospital(hospital: Hospital): BaseResponse<Any> {
    arangoDB.update(hospital)
    return BaseResponse.SuccessResponse(message = "Success")

  }

  override fun deleteHospital(hospitalId: String): BaseResponse<Any> {
    val hospital = arangoDB.one(
      Hospital::class,
      query = "FOR p IN hospital FILTER p._key LIKE ${hospitalId} RETURN p"
    )
    if (hospital != null) {
      arangoDB.delete(hospital)
      return BaseResponse.SuccessResponse(message = "Success")

    }
    return BaseResponse.ErrorResponse(message = "Cannot find Hospital to Delete")

  }

  override fun getAllHospitalPatient(ID: String): BaseResponse<Any> {
    val res = arangoDB.list(
      PatientHospitalRelation::class,
      query = "FOR h IN patienthospitalrelation FILTER h.target LIKE ${ID} RETURN h"
    )
    val patients = mutableListOf<Patient?>()
    res.forEach {
      patients.add(
        arangoDB.one(
          Patient::class,
          query = "FOR p IN patient FILTER p._key LIKE ${it.nodeId} RETURN p"
        )
      )
      println(it.nodeId)
    }
    println(patients.toString())

    return BaseResponse.SuccessResponse(data = patients.toString(), message = "Success")
  }

}
