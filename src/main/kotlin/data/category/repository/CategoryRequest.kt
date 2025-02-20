package com.example.data.category.repository

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class CategoryRequest(
    @SerialName("_id")
    val id: String = ObjectId().toString(),
    val name: String,
    val icon: String,
    val iconBackground: String
)