package com.example.data.habits

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList

class HabitRepositoryImpl(
    database: MongoDatabase
): HabitRepository {

    companion object{
        private val HABIT_COLLECTION = "Habits"
    }
    private val habits = database.getCollection<HabitResponse>(HABIT_COLLECTION)

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
            isCompleted = false
        )
        habits.insertOne(habitResponse)
    }

    override suspend fun getHabitsByUserId(userId: String): List<HabitResponse> {
        return habits.find(Filters.eq("userId", userId)).toList()
    }

    override suspend fun getHabitById(userId: String, habitId: String): HabitResponse? {
        return habits.find(
            Filters.and(
                Filters.eq("habitId", habitId),
                Filters.eq("userId", userId)
            )
        ).firstOrNull()
    }


    override suspend fun updateHabit(userId: String, habit: HabitCompletion): Boolean {
        val filter = Filters.and(
            Filters.eq("habitId", habit.habitId),
            Filters.eq("userId", userId)
        )
        val update = Updates.set("isCompleted", habit.isCompleted)
        val result = habits.updateOne(filter, update)
        return result.modifiedCount == 1L
    }

    override suspend fun deleteHabit(userId: String, habitId: String): Boolean  {
        val filter = Filters.and(
            Filters.eq("habitId", habitId),
            Filters.eq("userId", userId)
        )
        val result = habits.deleteOne(filter)
        return result.deletedCount == 1L
    }
}