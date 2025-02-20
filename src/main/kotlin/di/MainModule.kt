package com.example.di

import com.example.data.auth.user.UserDataSource
import com.example.data.auth.user.UserDataSourceImpl
import com.example.data.habits.HabitRepository
import com.example.data.habits.HabitRepositoryImpl
import com.example.data.habits.repository.CategoryRepository
import com.example.data.habits.repository.CategoryRepositoryImpl
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.kotlin.client.coroutine.MongoClient
import org.koin.dsl.module

val mainModule = module {
    single<MongoClient> {
        val connectionString = System.getenv("MONGO_URI")
        val serverApi = ServerApi.builder()
            .version(ServerApiVersion.V1)
            .build()
        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(ConnectionString(connectionString))
            .serverApi(serverApi)
            .build()

        MongoClient.create(mongoClientSettings)
    }
    single {
        val mongoClient: MongoClient = get()
        mongoClient.getDatabase("HabitTrack")
    }
    single<UserDataSource> {
        UserDataSourceImpl(get())
    }
    single<CategoryRepository>{
        CategoryRepositoryImpl(get())
    }
    single<HabitRepository> {
        HabitRepositoryImpl(get())
    }
}