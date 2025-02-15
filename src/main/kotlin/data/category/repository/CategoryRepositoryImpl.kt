package com.example.data.habits.repository

import com.example.data.habits.response.HabitCategory
import com.mongodb.client.MongoDatabase

class CategoryRepositoryImpl(
    db: MongoDatabase
): CategoryRepository {

    companion object{
        private val CATEGORY_COLLECTION = "Categories"
    }
    private val categories = db.getCollection<HabitCategory>(CATEGORY_COLLECTION, HabitCategory::class.java)

    override suspend fun insertAllCategory(category: List<HabitCategory>) {
        categories.insertMany(category)
    }

    override suspend fun getAllCategory(): List<HabitCategory> {
        return categories.find().toList()
    }


}