package com.example.repository

import com.example.data.User
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.Filters
import org.bson.conversions.Bson
import org.koin.java.KoinJavaComponent.inject

class UserDataSourceImpl(
    db : MongoDatabase
) : UserDataSource{

    companion object{
        private val USER_COLLECTION  = "User"
    }

    private val users = db.getCollection<User>(USER_COLLECTION, User::class.java)

    override suspend fun getUserByUsername(username: String): User? {
        val filter: Bson = Filters.eq("username", username)
        return users.find(filter).firstOrNull()
    }

    override suspend fun insertUser(user: User): Boolean {
        return users.insertOne(user).wasAcknowledged()
    }

}