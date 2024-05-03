package com.example

import com.example.client.hospitalservice.HospitalServiceImpl
import com.example.client.patientclient.PatientServiceImpl
import com.example.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
  io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
  configureSerialization()
  val aranogDb = configureDatabases()
  val hospitalService = HospitalServiceImpl(aranogDb)
  val patientService = PatientServiceImpl(aranogDb)
  configureMonitoring()
  configureSecurity()
  configureRouting(hospitalService, patientService)
}
