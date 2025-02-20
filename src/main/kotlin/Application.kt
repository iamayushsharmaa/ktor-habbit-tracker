package com.example

import com.example.data.auth.user.UserDataSource
import com.example.data.habits.HabitRepository
import com.example.di.mainModule
import com.example.security.hashing.SHA256HashingService
import com.example.security.token.JwtTokenService
import com.example.security.token.TokenConfig
import io.ktor.server.application.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(Koin) {
        slf4jLogger()
        modules(mainModule)
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

    val habitRepository by inject<HabitRepository>()

    configureSerialization()
    configureSecurity(tokenConfig)
    configureFrameworks()
    configureMonitoring()
    configureRouting(hashingService, userDataSource, tokenService, tokenConfig, habitRepository)
}
