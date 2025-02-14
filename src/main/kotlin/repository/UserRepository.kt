package com.example.repository

import com.example.data.User
import com.mongodb.client.MongoDatabase

interface UserDataSource{
    suspend fun getUserByUsername(username : String) : User?
    suspend fun insertUser(user : User) : Boolean
}