package com.example.data.habits

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.LocalDate

@Serializable
data class HabitCompletion(
    @BsonId val completionId: String = ObjectId().toString(),
    val habitId: String,
    @Contextual val date: LocalDate,
    val isCompleted: Boolean
)