package com.example

import com.example.data.User
import com.example.di.mainModule
import com.example.repository.UserDataSource
import com.example.repository.UserDataSourceImpl
import com.example.security.hashing.SHA256HashingService
import com.example.security.token.JwtTokenService
import com.example.security.token.TokenConfig
import com.example.security.token.TokenService
import com.mongodb.ConnectionString
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import io.ktor.server.application.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(Koin) {
        slf4jLogger()
        modules(org.koin.dsl.module {
            single<MongoClient> {
                val connectionString = System.getenv("MONGO_URI")
                MongoClients.create(connectionString)
            }
            single {
                val mongoClient: MongoClient = get()
                mongoClient.getDatabase("Habits")
            }
            single<UserDataSource> {
                UserDataSourceImpl(get())
            }
        })
    }

    val userDataSource by inject<UserDataSource>()

    val tokenService = JwtTokenService()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = System.getenv("JWT_SECRET")
    )
    val hashingService = SHA256HashingService()

    GlobalScope.launch {
        val user = User(
            username = "test_username",
            password = "test_password",
            salt = "salt"
        )
        userDataSource.insertUser(user)
    }

    configureSerialization()
    configureSecurity(tokenConfig)
    configureFrameworks()
    // configureDatabases()
    configureMonitoring()
    configureRouting()
}
