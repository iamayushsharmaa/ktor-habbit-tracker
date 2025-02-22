package com.example.data.habits

import kotlinx.serialization.Serializable

@Serializable
data class GoalRequest(
    val value: String,
    val unit: String
)
