package com.example.data.habits

import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerializationException
import java.time.LocalDate

fun Route.habit(
    habitRepository: HabitRepository
){
    authenticate {
        post("/habits") {
            try {
                val principal = call.principal<JWTPrincipal>()
                    ?: throw IllegalArgumentException("User not authenticated")
                val userId = principal.payload.getClaim("userId").asString()
                    ?: throw IllegalArgumentException("User ID not found in token")

                val habit = call.receive<HabitRequest>().copy(userId = userId)
                val habitId = habitRepository.createHabit(habit)
                call.respond(HttpStatusCode.Created, mapOf("habitId" to habitId))
            } catch (e: SerializationException) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request data: ${e.message}")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Server error: ${e.message}")
            }
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

            val request = try {
                call.receive<HabitCompletionRequest>()
            } catch (e: Exception) {
                return@put call.respond(HttpStatusCode.BadRequest, "Invalid request body")
            }
            val date = LocalDate.now()

            val result = habitRepository.completeHabit(
                habitId = habitId,
                date = date,
                isCompleted = request.isCompleted,
                userId = userId
            )

            when {
                result.isSuccess -> {
                    call.respond(HttpStatusCode.OK, result.getOrNull()!!)
                }
                result.isFailure -> {
                    val exception = result.exceptionOrNull()!!
                    when (exception) {
                        is IllegalArgumentException -> call.respond(HttpStatusCode.BadRequest, exception.message ?: "Bad Request")
                        is SecurityException -> call.respond(HttpStatusCode.Forbidden, exception.message ?: "Forbidden")
                        is IllegalStateException -> call.respond(HttpStatusCode.Conflict, exception.message ?: "Conflict")
                        else -> call.respond(HttpStatusCode.InternalServerError, "An error occurred: ${exception.message}")
                    }
                }
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