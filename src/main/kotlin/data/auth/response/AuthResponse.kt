package com.example.data.auth.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse (
    val token: String
)