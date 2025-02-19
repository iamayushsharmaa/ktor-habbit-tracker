package com.example.data.habits

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class HabitCompletion(
    val completionId: String,
    val habitId: String,
    val userId: String,
    @Contextual val date: LocalDate,
    val isCompleted: Boolean
)