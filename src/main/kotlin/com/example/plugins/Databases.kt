package com.example.plugins

import com.example.database.Db
import io.ktor.server.application.*


fun Application.configureDatabases(): Db {
  val arangoDB = Db("root" , database = "New_database" )
  return arangoDB

}


