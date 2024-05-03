package com.example.data.model

import com.arangodb.internal.DocumentFields
import com.arangodb.serde.jackson.Key
import com.arangodb.serde.jackson.Rev
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
open class Model {
    @Key
    @SerialName("id")
    @JsonNames(DocumentFields.KEY)
    var id: String? = null
    @Rev
    @SerialName("rev")
    @JsonNames(DocumentFields.REV)
    var rev: String? = null
    var created: Instant? = null
    var updated: Instant? = null
}
