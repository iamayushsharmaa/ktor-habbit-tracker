package com.example.data.habits.repository

import com.example.data.habits.response.CategoryResponse


interface CategoryRepository {
    suspend fun insertAllCategory(category: List<CategoryResponse>)
    suspend fun getAllCategory(): List<CategoryResponse>
}