package com.example.data.model

import com.arangodb.internal.DocumentFields
import com.arangodb.serde.jackson.From
import com.arangodb.serde.jackson.To
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class PatientHospitalRelation(
  var registeredHospital: List<Hospital>? = emptyList()
) : Edge()


@Serializable
data class HospitalPatientRelation(
  val registeredPatients: List<Patient> = emptyList()
) : Edge()

@Serializable
open class Edge : Model() {
  @From
  @SerialName("from")
  @JsonNames(DocumentFields.FROM)
  var from: String? = null

  @To
  @SerialName("to")
  @JsonNames(DocumentFields.TO)
  var to: String? = null
}

