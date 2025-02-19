package com.example

import com.example.data.auth.user.UserDataSource
import com.example.data.habits.HabitRepository
import com.example.data.habits.habit
import com.example.security.hashing.HashingService
import com.example.security.token.TokenConfig
import com.example.security.token.TokenService
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(
    hashingService: HashingService,
    userDataSource: UserDataSource,
    tokenService: TokenService,
    tokenConfig: TokenConfig,
    habitRepository: HabitRepository
) {
    routing {
        signUp(hashingService,userDataSource)
        signIn(hashingService,userDataSource,tokenService,tokenConfig)
        authenticate()
        getSecretInfo()

        //category(categoryRepository)
        habit(habitRepository)

        get("/") {
            call.respondText("Hello World!")
        }
    }
}
