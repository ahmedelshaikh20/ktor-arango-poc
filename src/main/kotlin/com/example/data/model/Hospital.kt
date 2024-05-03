package com.example.data.model

import kotlinx.serialization.Serializable


@Serializable
data class Hospital(
  val name: String,
  val type: String,
  val address: String,
) : Model()
