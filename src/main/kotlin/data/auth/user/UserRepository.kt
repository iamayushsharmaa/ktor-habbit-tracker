package com.example.data.auth.user

interface UserDataSource{
    suspend fun getUserByUsername(username : String) : User?
    suspend fun insertUser(user : User) : Boolean
}