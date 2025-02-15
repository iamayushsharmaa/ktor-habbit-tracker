package com.example.data.habits

interface HabitRepository {
    suspend fun createHabit(habit: Habit): String
    suspend fun getHabitsByUserId(userId: String): List<Habit>
    suspend fun updateHabit( userId: String, habit: Habit): Boolean
    suspend fun deleteHabit(userId: String, habitId: String): Boolean
}