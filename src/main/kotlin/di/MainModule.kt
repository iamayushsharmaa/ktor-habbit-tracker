package com.example.di

import com.example.data.auth.user.UserDataSource
import com.example.data.auth.user.UserDataSourceImpl
import com.mongodb.ConnectionString
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.koin.dsl.module

val mainModule = module {

    single<MongoClient> {
        val connectionString = ConnectionString(System.getenv("MONGO_URI"))
        MongoClients.create(connectionString)
    }

    single {
        val mongoClient: MongoClient = get()
        mongoClient.getDatabase("Habits")
    }

    single<UserDataSource> {
        UserDataSourceImpl(get())
    }
}