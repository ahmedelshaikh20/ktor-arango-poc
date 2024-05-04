package com.example.client.hospitalservice

import com.example.data.model.Hospital
import com.example.data.model.Patient
import com.example.utils.BaseResponse

interface HospitalService {
  fun getAllHospitals(): BaseResponse<Any>
  fun createHospital(hospital: Hospital): BaseResponse<Any>
  fun modifyHospital(hospital: Hospital): BaseResponse<Any>
  fun deleteHospital(hospitalId: String): BaseResponse<Any>
  fun getAllHospitalPatient(hospitalID: String): BaseResponse<Any>
}
