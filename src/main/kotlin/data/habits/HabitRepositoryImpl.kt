package com.example.data.habits

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList

class HabitRepositoryImpl(
    database: MongoDatabase
): HabitRepository {

    companion object{
        private val HABIT_COLLECTION = "Habits"
    }
    private val habits = database.getCollection<Habit>(HABIT_COLLECTION)

    override suspend fun createHabit(habit: Habit): String {
        habits.insertOne(habit)
        return habit.id
    }

    override suspend fun getHabitsByUserId(userId: String): List<Habit> {
        return habits.find(Filters.eq("userId", userId)).toList()
    }

    override suspend fun getHabitById(userId: String, habitId: String): Habit? {
        return habits.find(
            Filters.and(
                Filters.eq("_id", habitId),
                Filters.eq("userId", userId)
            )
        ).firstOrNull()
    }


    override suspend fun updateHabit(userId: String,habit: Habit): Boolean {
        val filter = Filters.and(
            Filters.eq("_id", habit.id),
            Filters.eq("userId", userId)
        )
        val result = habits.replaceOne(filter, habit)
        return result.modifiedCount == 1L
    }

    override suspend fun deleteHabit(userId: String, habitId: String): Boolean {
        val filter = Filters.and(
            Filters.eq("_id", habitId),
            Filters.eq("userId", userId)
        )
        val result = habits.deleteOne(filter)
        return result.deletedCount == 1L

    }
}