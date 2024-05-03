package com.example.di


import com.arangodb.ArangoDB

class AppModule {
  companion object {
    lateinit var arongoDB: ArangoDB
      private set

    fun initialize(): ArangoDB {
      synchronized(this) {
        if (!this::arongoDB.isInitialized) {
          arongoDB = ArangoDB.Builder().user("").host("localhost", 8529).build()
        }
      }
      return arongoDB
    }

  }
}
