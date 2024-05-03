package com.example.database

import com.arangodb.entity.CollectionType
import com.arangodb.model.GeoIndexOptions
import com.example.data.model.Hospital
import com.example.data.model.HospitalPatientRelation
import com.example.data.model.Patient
import com.example.data.model.PatientHospitalRelation


fun collections() = listOf(
  collection<Hospital> {
    index(Hospital::name)
  },
  collection<Patient> {
    index(Patient::name)
  },
  collection<PatientHospitalRelation>(
    collectionType = CollectionType.EDGES,
    nodes = listOf(Patient::class),
    targets = listOf(Hospital::class)
  )
)

