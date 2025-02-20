package com.example.data.habits

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.LocalDate

fun Route.habit(
    habitRepository: HabitRepository
){
    authenticate("auth-jwt") {
        post("/habits") {
            val principal = call.principal<JWTPrincipal>()
                ?: throw IllegalArgumentException("User not authenticated")
            val userId = principal.payload.getClaim("userId").asString()
                ?: throw IllegalArgumentException("User ID not found in token")

            val habit = call.receive<HabitRequest>().copy(userId = userId)
            val habitId = habitRepository.createHabit(habit)
            call.respond(HttpStatusCode.Created, mapOf("habitId" to habitId))
        }

        get("/habits") {
            val principal = call.principal<JWTPrincipal>()
                ?: throw IllegalArgumentException("User not authenticated")
            val userId = principal.payload.getClaim("userId").asString()
                ?: throw IllegalArgumentException("User ID not found in token")
            val date = call.request.queryParameters["date"]?.let { LocalDate.parse(it) } ?: LocalDate.now()
            val habits = habitRepository.getHabitsByUserId(userId, date)
            call.respond(habits)
        }

        get("/habits/{id}") {
            val principal = call.principal<JWTPrincipal>()
                ?: throw IllegalArgumentException("User not authenticated")
            val userId = principal.payload.getClaim("userId").asString()
                ?: throw IllegalArgumentException("User ID not found in token")

            val habitId = call.parameters["id"]
                ?: throw IllegalArgumentException("Habit ID is required")
            val habit = habitRepository.getHabitById(userId, habitId)
            if (habit != null) {
                call.respond(habit)
            } else {
                call.respond(HttpStatusCode.NotFound, "Habit not found or does not belong to the user")
            }
        }

        put("/habits/{id}") {
            val principal = call.principal<JWTPrincipal>()
                ?: throw IllegalArgumentException("User not authenticated")
            val userId = principal.payload.getClaim("userId").asString()
                ?: throw IllegalArgumentException("User ID not found in token")

            val habitId = call.parameters["id"]
                ?: throw IllegalArgumentException("Habit ID is required")

            val habit = call.receive<HabitCompletion>()

            if (habit.habitId != habitId) {
                throw IllegalArgumentException("Habit ID in the body does not match the URL")
            }
            val isUpdated = habitRepository.updateHabit(userId, habit)
            if (isUpdated) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound, "Habit not found or does not belong to the user")
            }
        }

        delete("/habits/{id}") {
            val principal = call.principal<JWTPrincipal>()
                ?: throw IllegalArgumentException("User not authenticated")
            val userId = principal.payload.getClaim("userId").asString()
                ?: throw IllegalArgumentException("User ID not found in token")

            val habitId = call.parameters["id"]
                ?: throw IllegalArgumentException("Habit ID is required")

            val isDeleted = habitRepository.deleteHabit(userId, habitId)
            if (isDeleted) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound, "Habit not found or does not belong to the user")
            }
        }
    }
}