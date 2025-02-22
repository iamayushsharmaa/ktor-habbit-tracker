package com.example.data.habits

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import java.time.LocalDate
import java.time.LocalDateTime

class HabitRepositoryImpl(
    database: MongoDatabase
): HabitRepository {

    companion object{
        private val HABIT_COLLECTION = "Habits"
        private val COMPLETION_COLLECTION = "Habit Completion"
    }
    private val habits = database.getCollection<HabitResponse>(HABIT_COLLECTION)
    private val completionsCollection = database.getCollection<HabitCompletion>(COMPLETION_COLLECTION)

    override suspend fun createHabit(habit: HabitRequest) {
        val habitResponse = HabitResponse(
            habitId = habit.habitId,
            userId = habit.userId,
            name = habit.name,
            icon = habit.icon,
            iconBackground = habit.iconBackground,
            description = habit.description,
            frequency = habit.frequency,
            startDate = habit.startDate,
            isActive = habit.isActive,
            isLocked = false,
            isCompleted = false,
            value = habit.goal.value,
            unit = habit.goal.unit
        )
        habits.insertOne(habitResponse)
    }

    override suspend fun getHabitsByUserId(userId: String, date: LocalDate): List<HabitResponse> {
        val currentDate = LocalDate.now()

        val completions = completionsCollection.find(Filters.eq("habitId", Filters.eq("userId", userId))).toList()

        return habits.find(Filters.eq("userId", userId)).toList().map { habit ->

            val isDateValid = !date.isBefore(habit.startDate)
            val isLocked = when {
                !isDateValid -> true
                date.isBefore(currentDate) -> true
                date.isAfter(currentDate) -> true
                else -> false
            }

            val completionForDate = completions.find {
                it.habitId == habit.habitId && it.date == date
            }
            val isCompleted = completionForDate?.isCompleted ?: false
            val habitHistory = completions.filter { it.habitId == habit.habitId }

            HabitResponse(
                habitId = habit.habitId,
                userId = habit.userId,
                name = habit.name,
                icon = habit.icon,
                iconBackground = habit.iconBackground,
                description = habit.description,
                frequency = habit.frequency,
                startDate = habit.startDate,
                isActive = habit.isActive,
                isLocked = isLocked,
                isCompleted = isCompleted,
                completionHistory = habitHistory,
                value = habit.value,
                unit = habit.unit
            )
        }
    }

    override suspend fun getHabitById(userId: String, habitId: String): HabitResponse? {
        return habits.find(
            Filters.and(
                Filters.eq("habitId", habitId),
                Filters.eq("userId", userId)
            )
        ).firstOrNull()
    }


    override suspend fun deleteHabit(userId: String, habitId: String): Boolean  {
        val filter = Filters.and(
            Filters.eq("habitId", habitId),
            Filters.eq("userId", userId)
        )
        val result = habits.deleteOne(filter)
        return result.deletedCount == 1L
    }

    override suspend fun updateGoal(userId: String, habitId: String, goal: GoalRequest) {

    }


    override suspend fun completeHabit(
        habitId: String,
        date: LocalDate,
        isCompleted: Boolean,
        userId: String?
    ): Result<HabitCompletion> {
        return try {
            val currentDate = LocalDate.now()

            if (date.isAfter(currentDate)) {
                throw IllegalArgumentException("Cannot complete habits for future dates")
            }
            if (date.isBefore(currentDate)) {
                throw IllegalArgumentException("Cannot modify completion status for past dates")
            }
            val habit = habits.find(Filters.eq("habitId", habitId)).firstOrNull()
                ?: throw IllegalArgumentException("Habit with ID $habitId not found")

            if (userId != null && habit.userId != userId) {
                throw SecurityException("User $userId is not authorized to modify habit $habitId")
            }

            if (date.isBefore(habit.startDate)) {
                throw IllegalStateException("Habit has not yet started as of $date")
            }

            val existingCompletion = completionsCollection.find(
                Filters.and(
                    Filters.eq("habitId", habitId),
                    Filters.eq("date", date)
                )
            ).firstOrNull()

            val completionRecord = if (existingCompletion != null) {
                val updatedRecord = existingCompletion.copy(
                    isCompleted = isCompleted,
                )
                completionsCollection.updateOne(
                    Filters.eq("completionId", existingCompletion.completionId),
                    Updates.combine(
                        Updates.set("isCompleted", isCompleted),
                        Updates.set("completedAt", if (isCompleted) LocalDateTime.now() else null)
                    )
                )
                updatedRecord
            } else {
                val newCompletion = HabitCompletion(
                    habitId = habitId,
                    date = date,
                    isCompleted = isCompleted,
                )
                completionsCollection.insertOne(newCompletion)
                newCompletion
            }

            Result.success(completionRecord)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}