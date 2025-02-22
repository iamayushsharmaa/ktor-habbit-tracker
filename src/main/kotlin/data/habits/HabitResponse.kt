package com.example.data.habits

import data.habits.LocalDateSerializer
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class HabitResponse(
    val habitId: String,
    val userId: String,
    val name: String,
    val icon: String,
    val iconBackground: String,
    val description: String,
    val value: String,
    val unit: String,
    val frequency: Frequency,
    @Serializable(with = LocalDateSerializer::class)  val startDate: LocalDate,
    val isActive: Boolean,
    val isLocked: Boolean,
    val isCompleted: Boolean,
    val completionHistory: List<HabitCompletion> = emptyList()
)