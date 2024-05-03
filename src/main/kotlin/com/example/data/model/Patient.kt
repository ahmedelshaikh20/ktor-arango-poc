package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Patient(
  val name: String,
  val gender: String,
) : Model()
