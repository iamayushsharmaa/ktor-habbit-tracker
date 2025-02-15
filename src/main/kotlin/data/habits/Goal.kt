package com.example.data.habits

import kotlinx.serialization.Serializable

@Serializable
data class Goal(
    val unit: String,
    val value: Int,
    val periodicity: String
)