package com.example.data.habits

import com.example.data.habits.response.HabitCategory
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class Habit (
    @BsonId val id: String = ObjectId().toString(),
    val userId: String,
    val name: String,
    val description: String,
    val category: HabitCategory,
    val startDate: Long,
    val goal: Goal,
    val completed: Boolean = false
)