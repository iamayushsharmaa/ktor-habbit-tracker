package com.example.data.habits.repository

import com.example.data.habits.response.CategoryResponse


object Categories {
    val categories = listOf(
        CategoryResponse(name = "Health", icon = "/static/icons/health.svg", iconBackground = "#FF5733"),
        CategoryResponse(name = "Fitness", icon = "/static/icons/fitness.svg", iconBackground = "#4CAF50"),
        CategoryResponse(name = "Mindfulness", icon = "/static/icons/meditation.svg", iconBackground = "#2196F3"),
        CategoryResponse(name = "Learning", icon = "/static/icons/book.svg", iconBackground = "#9C27B0"),
        CategoryResponse(name = "Work", icon = "/static/icons/work.svg", iconBackground = "#4CAF50"),
        CategoryResponse(name = "Productivity", icon = "/static/icons/productivity.com", iconBackground = "#FFC107"),
        CategoryResponse(name = "Social", icon = "/static/icons/social.svg", iconBackground = "#E91E63"),
        CategoryResponse(name = "Hobbies", icon = "/static/icons/hobbies.svg", iconBackground = "#673AB7"),
        CategoryResponse(name = "Finance", icon = "/static/icons/finance.svg", iconBackground = "#00BCD4"),
        CategoryResponse(name = "Self-Care", icon = "/static/icons/self_improvement.svg", iconBackground = "#8BC34A"),

    )
}