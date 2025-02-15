package com.example.data.habits

import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.habit(
    habitRepository: HabitRepository
){
    post("/habits") {
        val habit = call.receive<Habit>()
        val habitId = habitRepository.createHabit(habit)
        call.respond(HttpStatusCode.Created, mapOf("id" to habitId))
    }

    get("/habits") {
        val userId = call.request.queryParameters["userId"]
            ?: throw IllegalArgumentException("User ID is required")
        val habits = habitRepository.getHabitsByUserId(userId)
        call.respond(habits)
    }

    put("/habits/{id}") {
        val habitId = call.parameters["id"]
            ?: throw IllegalArgumentException("Habit ID is required")
        val userId = call.request.queryParameters["userId"]
            ?: throw IllegalArgumentException("User ID is required")
        val habit = call.receive<Habit>()
        if (habit.id != habitId) {
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
        val habitId = call.parameters["id"]
            ?: throw IllegalArgumentException("Habit ID is required")
        val userId = call.request.queryParameters["userId"]
            ?: throw IllegalArgumentException("User ID is required")
        val isDeleted = habitRepository.deleteHabit(userId, habitId)
        if (isDeleted) {
            call.respond(HttpStatusCode.OK)
        } else {
            call.respond(HttpStatusCode.NotFound, "Habit not found or does not belong to the user")
        }
    }
}