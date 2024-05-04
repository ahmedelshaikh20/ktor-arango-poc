package com.example.client.patientclient

import com.example.data.model.Hospital
import com.example.data.model.Patient
import com.example.data.register.HospitalRegistrationBody
import com.example.utils.BaseResponse

interface PatientService {
  suspend fun getAllPatients(): BaseResponse<Any>
  suspend fun addPatient(patient: Patient): BaseResponse<Any>
  suspend fun deletePatient(patientID: String): BaseResponse<Any>
  suspend fun modifyPatient(patient: Patient): BaseResponse<Any>
  suspend fun registerHospital(hospitalRegistrationBody: HospitalRegistrationBody): BaseResponse<Any>
  suspend fun getAllRegisteredHospitals(patientId: String): BaseResponse<Any>

}
