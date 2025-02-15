package com.example.data.habits.response

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.awt.Color

@Serializable
data class HabitCategory(
    @BsonId val id: String = ObjectId().toString(),
    val name: String,
    val icon: String,
    val iconBackground: String
)
