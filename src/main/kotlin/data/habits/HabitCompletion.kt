package com.example.data.habits

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.UUID

@Serializable
data class HabitCompletion(
    val completionId: String = UUID.randomUUID().toString(),
    val habitId: String,
    @Contextual val date: LocalDate,
    val isCompleted: Boolean,
)