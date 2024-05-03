package com.example.database

import com.arangodb.*
import com.arangodb.entity.CollectionType
import com.arangodb.model.PersistentIndexOptions
import com.example.data.model.Model
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleDeserializers
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.module.SimpleSerializers
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.IOException
import java.time.format.DateTimeFormatter
import kotlin.reflect.KClass
import kotlin.reflect.KProperty


class InstantDeserializer : StdDeserializer<Instant>(Instant::class.java) {
  override fun deserialize(jsonParser: JsonParser, obj: DeserializationContext): Instant {
    val value = jsonParser.codec.readValue(jsonParser, String::class.java)

    return Instant.parse(value)
  }
}

class InstantSerializer : StdSerializer<Instant>(Instant::class.java) {
  @Throws(IOException::class)
  override fun serialize(value: Instant, jsonGenerator: JsonGenerator, serializerProvider: SerializerProvider) {
    jsonGenerator.writeString(DateTimeFormatter.ISO_INSTANT.format(value.toJavaInstant()))
  }
}

class DbModule : SimpleModule() {
  override fun getModuleName(): String = this.javaClass.simpleName

  override fun setupModule(context: SetupContext) {
    val serializers = SimpleSerializers()
    serializers.addSerializer(Instant::class.java, InstantSerializer())
    context.addSerializers(serializers)

    val deserializers = SimpleDeserializers()
    deserializers.addDeserializer(Instant::class.java, InstantDeserializer())
    context.addDeserializers(deserializers)
  }
}


inline fun <reified T : Model> collection(
  collectionType: CollectionType = CollectionType.DOCUMENT,
  nodes: List<KClass<out Model>> = listOf(),
  targets: List<KClass<out Model>> = listOf(),
  noinline block: ArangoCollection.() -> Unit = {}
) = CollectionConfig(
  T::class.graph,
  T::class.collection,
  collectionType,
  nodes,
  targets,
  block
)

class CollectionConfig(
  val graph: String,
  val name: String,
  val collectionType: CollectionType,
  val nodes: List<KClass<out Model>> = listOf(),
  val targets: List<KClass<out Model>> = listOf(),
  val block: ArangoCollection.() -> Unit
)

internal fun String.asKey() = this.split("/").last()

internal fun <T : Model> String.asId(klass: KClass<T>) = if (this.contains("/")) this else "${klass.collection}/$this"

val <T : Model> KClass<T>.collection get() = simpleName!!.lowercase()
val <T : Model> KClass<T>.graph get() = "${collection}-graph"

fun f(property: KProperty<*>) = property.name
inline fun <reified T : Any> v(value: T) = Json.encodeToString(value)

fun ArangoCollection.index(vararg fields: KProperty<*>, options: PersistentIndexOptions = PersistentIndexOptions()) {
  ensurePersistentIndex(fields.toList().map { f(it) }, options)
}
