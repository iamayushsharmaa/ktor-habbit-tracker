package com.example.data.habits.repository

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.toList

//class CategoryRepositoryImpl(
//    database: MongoDatabase
//): CategoryRepository {

//    companion object{
//        private val CATEGORY_COLLECTION = "Categories"
//    }
//    private val categories = database.getCollection<CategoryResponse>(CATEGORY_COLLECTION)
//
//    override suspend fun insertAllCategory(category: List<CategoryResponse>) {
//        categories.insertMany(category)
//    }
//
//    override suspend fun getAllCategory(): List<CategoryResponse> {
//        return categories.find().toList()
//    }
//    }