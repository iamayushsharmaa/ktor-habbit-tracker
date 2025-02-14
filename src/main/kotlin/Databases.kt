package com.example

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.data.User
import com.example.repository.UserDataSourceImpl
import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.ServerApi
import com.mongodb.ServerApiVersion
import com.mongodb.client.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.slf4j.event.*

fun Application.configureDatabases() {
//    val mongoDatabase = connectToMongoDB()
//    val userDataSource = UserDataSourceImpl(mongoDatabase)
//    GlobalScope.launch {
//        val user = User(
//            username = "test_username",
//            password = "test_password",
//            salt = "salt"
//        )
//        userDataSource.insertUser(user)
//    }
}


//fun Application.connectToMongoDB(): MongoDatabase {
//
//    val connectionString = "mongodb+srv://ayushs9468:ayushs9468@cluster0.xvkqh.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
//    val serverApi = ServerApi.builder()
//        .version(ServerApiVersion.V1)
//        .build()
//    val mongoClientSettings = MongoClientSettings.builder()
//        .applyConnectionString(ConnectionString(connectionString))
//        .serverApi(serverApi)
//        .build()
//
//    val mongoClient = MongoClients.create(mongoClientSettings)
//    val database = mongoClient.getDatabase("Habits")
//
//    monitor.subscribe(ApplicationStopped) {
//        mongoClient.close()
//    }
//
//    return database
//}
