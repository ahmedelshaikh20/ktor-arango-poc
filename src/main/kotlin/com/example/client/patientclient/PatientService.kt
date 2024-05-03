package com.example.client.patientclient

import com.example.data.model.Hospital
import com.example.data.model.Patient
import com.example.data.register.HospitalRegistrationBody

interface PatientService {
  suspend fun getAllPatients(): List<Patient>
  suspend fun addPatient(patient: Patient)
  suspend fun deletePatient(patientID:String)
  suspend fun modifyPatient(patient: Patient)
  suspend fun registerHospital(hospitalRegistrationBody: HospitalRegistrationBody)
  suspend fun getAllRegisteredHospitals(patientId: String): List<Hospital>

}
