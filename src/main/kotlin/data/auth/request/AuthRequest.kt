package com.example.data.auth.request

import kotlinx.serialization.Serializable


data class AuthRequest(
    val username: String,
    val password: String
)