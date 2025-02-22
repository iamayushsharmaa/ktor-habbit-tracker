package com.example.data.habits

import data.habits.LocalDateSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class HabitRequest(
    val habitId: String,
    val userId: String? = null,
    val name: String,
    val icon: String,
    val iconBackground: String,
    val description: String,
    val goal: Goal,
    val frequency: Frequency,
    @Serializable(with = LocalDateSerializer::class) val startDate: LocalDate,
    val isActive: Boolean
)

@Serializable
data class Goal(
    val value: String,
    val unit: String
)

enum class Frequency {
    EVERYDAY,
    ALTERNATE,
    WEEKLY,
    MONTHLY
}