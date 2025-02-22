package com.example.data.habits

import java.time.LocalDate

interface HabitRepository {
    suspend fun createHabit(habit: HabitRequest)
    suspend fun getHabitsByUserId(userId: String, date: LocalDate): List<HabitResponse>
    suspend fun getHabitById(userId: String, habitId: String): HabitResponse?
    suspend fun deleteHabit(userId: String, habitId: String): Boolean
    suspend fun updateGoal(userId: String, habitId: String, goal: GoalRequest)
    suspend fun completeHabit(
        habitId: String,
        date: LocalDate,
        isCompleted: Boolean,
        userId: String?
    ): Result<HabitCompletion>
}