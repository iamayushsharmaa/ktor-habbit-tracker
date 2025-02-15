package com.example.data.habits.repository

import com.example.data.habits.response.HabitCategory

interface CategoryRepository {
    suspend fun insertAllCategory(category: List<HabitCategory>)
    suspend fun getAllCategory(): List<HabitCategory>
}