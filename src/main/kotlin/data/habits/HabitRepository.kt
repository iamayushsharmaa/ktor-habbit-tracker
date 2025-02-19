package com.example.data.habits

interface HabitRepository {
    suspend fun createHabit(habit: HabitRequest)
    suspend fun getHabitsByUserId(userId: String): List<HabitResponse>
    suspend fun getHabitById(userId: String, habitId: String): HabitResponse?
    suspend fun updateHabit( userId: String, habit: HabitCompletion): Boolean
    suspend fun deleteHabit(userId: String, habitId: String): Boolean
}