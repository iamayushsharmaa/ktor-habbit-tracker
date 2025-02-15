package com.example.data.category.repository

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class CategoryRequest(
    @BsonId val id: String = ObjectId().toString(),
    val name: String,
    val icon: String,
    val iconBackground: String
)