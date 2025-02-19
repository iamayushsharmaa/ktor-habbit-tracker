package com.example.data.habits

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class HabitRequest(
    val habitId: String,
    val userId: String,
    val name: String,
    val icon: String,
    val iconBackground: String,
    val description: String,
    val frequency: Frequency,
    @Contextual val startDate: LocalDate,
    val isActive: Boolean
)

enum class Frequency {
    DAILY, WEEKLY, MONTHLY
}