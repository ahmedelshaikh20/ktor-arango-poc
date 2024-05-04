package com.example.database

import com.arangodb.ArangoDB
import com.arangodb.ArangoDBException
import com.arangodb.ArangoDatabase
import com.arangodb.ContentType
import com.arangodb.entity.CollectionType
import com.arangodb.entity.EdgeDefinition
import com.arangodb.model.CollectionCreateOptions
import com.arangodb.model.DocumentCreateOptions
import com.arangodb.model.DocumentUpdateOptions
import com.arangodb.serde.jackson.JacksonSerde
import com.example.data.model.Model
import com.fasterxml.jackson.module.kotlin.KotlinModule
import kotlin.reflect.KClass

class Db(user: String, database: String) {
  private val db = ArangoDB.Builder()
    .host("127.0.0.1", 8529)
    .user(user)
    .serde(JacksonSerde.of(ContentType.JSON).apply {
      configure {
        it.registerModule(DbModule())
        it.registerModule(KotlinModule.Builder().build())
      }
    })
    .build()
    .db(database)
    .setup()

  internal fun <T : Model> one(klass: KClass<T>, query: String, parameters: Map<String, Any?> = mapOf()) =
    synchronized(db) {
      db.query(
        query,
        klass.java,
        if (query.contains("@@collection")) mutableMapOf("@collection" to klass.collection) + parameters else parameters
      )
    }.stream().findFirst().takeIf { it.isPresent }?.get()

  internal fun <T : Model> list(klass: KClass<T>, query: String, parameters: Map<String, Any?> = mapOf()) =
    synchronized(db) {
      db.query(
        query,
        klass.java,
        if (query.contains("@@collection")) mutableMapOf("@collection" to klass.collection) + parameters else parameters
      )
    }.asListRemaining().toList()

  internal fun <T : Any> query(klass: KClass<T>, query: String, parameters: Map<String, Any?> = mapOf()) =
    synchronized(db) {
      db.query(
        query,
        klass.java,
        parameters
      ).asListRemaining()
    }.toList()

  fun <T : Model> insert(model: T) = synchronized(db) {
    db.collection(model::class.collection).insertDocument(
      model,
      DocumentCreateOptions().returnNew(true)
    )!!.new!!
  }

  fun <T : Model> update(model: T) = synchronized(db) {
    db.collection(model::class.collection)
      .updateDocument(model.id?.asKey(), model, DocumentUpdateOptions().returnNew(true))!!.new!!
  }

  fun <T : Model> delete(model: T) =
    synchronized(db) { db.collection(model::class.collection).deleteDocument(model.id?.asKey())!! }

  fun <T : Model> document(klass: KClass<T>, key: String) = synchronized(db) {
    try {
      db.collection(klass.collection).getDocument(key.asKey(), klass.java)
    } catch (e: ArangoDBException) {
      null
    }
  }
}

private fun ArangoDatabase.setup(): ArangoDatabase {
  collections().forEach { model ->
    try {
      println("Createed  ${model.name}")
      createCollection(
        model.name,
        CollectionCreateOptions().type(model.collectionType)
      )
    } catch (ignored: ArangoDBException) {
      println(ignored.message)
    }

    model.block(collection(model.name))

    if (model.collectionType == CollectionType.EDGES) {
      try {
        createGraph(
          model.graph, listOf(
            EdgeDefinition().collection(model.name)
              .from(*model.nodes.map { it.collection }.toTypedArray())
              .to(*model.targets.map { it.collection }.toTypedArray())
          )
        )
      } catch (ignored: ArangoDBException) {
        // Most likely already exists
      }
    }
  }

  return this
}
