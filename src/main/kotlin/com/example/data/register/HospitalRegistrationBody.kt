package com.example.data.register

import kotlinx.serialization.Serializable

@Serializable
data class HospitalRegistrationBody(
  val patientId: String,
  val hospitalId: String
)
