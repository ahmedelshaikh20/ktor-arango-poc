package com.example.client.hospitalservice

import com.example.data.model.Hospital
import com.example.data.model.Patient

interface HospitalService {
  fun getAllHospitals(): List<Hospital>
  fun createHospital(hospital: Hospital)
  fun modifyHospital(hospital: Hospital)
  fun deleteHospital(hospitalId: String)
  fun getAllHospitalPatient(hospitalID: String):List<Patient>
}
