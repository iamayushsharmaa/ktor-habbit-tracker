package com.example.repository

import com.example.data.User
import com.mongodb.client.MongoDatabase

class UserRepository(database: MongoDatabase) {
//    private val users = database.getCollection<User>()
//
//    suspend fun createUser(user: User): Boolean {
//        return users.insertOne(user).wasAcknowledged()
//    }
//
//    suspend fun findUserByUsername(username: String): User? {
//        return users.findOne(User::username eq username)
//    }
}