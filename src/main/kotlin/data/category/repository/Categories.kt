package com.example.data.habits.repository

import com.example.data.habits.response.HabitCategory

object Categories {
    val categories = listOf(
        HabitCategory(name = "Health", icon = "💪", iconBackground = "#FF5733"),
        HabitCategory(name = "Fitness", icon = "🏋️", iconBackground = "#4CAF50"),
        HabitCategory(name = "Mindfulness", icon = "🧘", iconBackground = "#2196F3"),
        HabitCategory(name = "Learning", icon = "📚", iconBackground = "#9C27B0"),
        HabitCategory(name = "Productivity", icon = "⏳", iconBackground = "#FFC107"),
        HabitCategory(name = "Social", icon = "👥", iconBackground = "#E91E63"),
        HabitCategory(name = "Hobbies", icon = "🎨", iconBackground = "#673AB7"),
        HabitCategory(name = "Finance", icon = "💰", iconBackground = "#00BCD4"),
        HabitCategory(name = "Self-Care", icon = "🧖", iconBackground = "#8BC34A"),
        HabitCategory(name = "Environment", icon = "🌱", iconBackground = "#4CAF50")
    )
}