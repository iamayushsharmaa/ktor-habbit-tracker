package com.example.data.habits.repository

import com.example.data.habits.response.HabitCategory

object Categories {
    val categories = listOf(
        HabitCategory(name = "Health", icon = "/static/icons/health.svg", iconBackground = "#FF5733"),
        HabitCategory(name = "Fitness", icon = "/static/icons/fitness.svg", iconBackground = "#4CAF50"),
        HabitCategory(name = "Mindfulness", icon = "/static/icons/meditation.svg", iconBackground = "#2196F3"),
        HabitCategory(name = "Learning", icon = "/static/icons/book.svg", iconBackground = "#9C27B0"),
        HabitCategory(name = "Work", icon = "/static/icons/work.svg", iconBackground = "#4CAF50"),
        HabitCategory(name = "Productivity", icon = "/static/icons/productivity.com", iconBackground = "#FFC107"),
        HabitCategory(name = "Social", icon = "/static/icons/social.svg", iconBackground = "#E91E63"),
        HabitCategory(name = "Hobbies", icon = "/static/icons/hobbies.svg", iconBackground = "#673AB7"),
        HabitCategory(name = "Finance", icon = "/static/icons/finance.svg", iconBackground = "#00BCD4"),
        HabitCategory(name = "Self-Care", icon = "/static/icons/self_improvement.svg", iconBackground = "#8BC34A"),

    )
}