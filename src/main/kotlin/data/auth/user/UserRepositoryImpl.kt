package com.example.data.auth.user


import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import org.bson.conversions.Bson

class UserDataSourceImpl(
    database : MongoDatabase
) : UserDataSource {

    companion object{
        private val USER_COLLECTION  = "User"
    }


    val users = database.getCollection<User>(USER_COLLECTION)

    override suspend fun getUserByUsername(username: String): User? {
        return try {
            val filter: Bson = Filters.eq(User::username.name, username)
            users.find(filter).firstOrNull()
        } catch (e: Exception) {
            e.printStackTrace() // Log the error for debugging
            null
        }
    }

    override suspend fun insertUser(user: User): Boolean {
        return try {
            users.insertOne(user).wasAcknowledged()
        } catch (e: Exception) {
            e.printStackTrace() // Log the error for debugging
            false
        }
    }
}