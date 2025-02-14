package com.example

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
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
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.slf4j.event.*

fun Application.configureDatabases() {
    val mongoDatabase = connectToMongoDB()
    routing {

    }
}


fun Application.connectToMongoDB(): MongoDatabase {
    val mongoPw = System.getenv("MONGO_PW") ?: throw IllegalStateException("Missing MongoDB Password")
    val uri = "mongodb+srv://ayushs9468:$mongoPw@cluster0.xvkqh.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"

    val mongoClient = MongoClients.create(uri)
    val database = mongoClient.getDatabase("habits")

    environment.monitor.subscribe(ApplicationStopped) {
        mongoClient.close()
    }

    return database
}
