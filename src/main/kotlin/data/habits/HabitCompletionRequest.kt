package com.example.data.habits

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.util.*

data class HabitCompletionRequest(
    val isCompleted: Boolean,
)