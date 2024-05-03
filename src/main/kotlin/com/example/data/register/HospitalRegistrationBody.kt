package com.example.data.register

import com.example.data.model.Hospital
import kotlinx.serialization.Serializable

@Serializable
data class HospitalRegistrationBody(
  val patientId: String,
  val hospital: Hospital
)
