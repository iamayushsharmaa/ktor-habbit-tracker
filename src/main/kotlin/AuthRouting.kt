package com.example

import com.example.data.auth.request.AuthRequest
import com.example.data.auth.response.AuthResponse
import com.example.data.auth.user.User
import com.example.data.auth.user.UserDataSource
import com.example.security.hashing.HashingService
import com.example.security.hashing.SaltedHash
import com.example.security.token.TokenClaim
import com.example.security.token.TokenConfig
import com.example.security.token.TokenService
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.signUp(
    hashingService: HashingService,
    userDataSource: UserDataSource
) {
    post("signup") {
        val request = call.receiveNullable<AuthRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "Invalid request body")
            return@post
        }

        when {
            request.username.isBlank() || request.password.isBlank() -> {
                call.respond(HttpStatusCode.BadRequest, "Username and password cannot be blank")
                return@post
            }
            request.password.length < 8 -> {
                call.respond(HttpStatusCode.BadRequest, "Password must be at least 8 characters")
                return@post
            }
        }

        val saltedHash = hashingService.generateSaltedHash(request.password)
        val user = User(
            username = request.username,
            password = saltedHash.hash,
            salt = saltedHash.salt
        )
        val wasAcknowledged = userDataSource.insertUser(user)
        if (!wasAcknowledged) {
            call.respond(HttpStatusCode.Conflict, "Username already exists")
            return@post
        }
        call.respond(HttpStatusCode.OK, "User created successfully")
    }
}

fun Route.signIn(
    hashingService: HashingService,
    userDataSource: UserDataSource,
    tokenService: TokenService,
    tokenConfig: TokenConfig
) {
    post("signin") {
        val request = call.receiveNullable<AuthRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, "Invalid request body")
            return@post
        }

        val user = userDataSource.getUserByUsername(request.username)
        if (user == null || !hashingService.verify(
                value = request.password,
                saltedHash = SaltedHash(
                    hash = user.password,
                    salt = user.salt
                )
            )
        ) {
            call.respond(HttpStatusCode.Unauthorized, "Incorrect username or password")
            return@post
        }

        val token = tokenService.generate(
            config = tokenConfig,
            TokenClaim(
                name = "userId",
                value = user.id
            )
        )

        call.respond(
            status = HttpStatusCode.OK,
            message = AuthResponse(
                token = token
            )
        )
    }
}

fun Route.authenticate() {
    authenticate {
        get("authenticate") {
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Route.getSecretInfo() {
    authenticate {
        get("secret") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)
            if (userId == null) {
                call.respond(HttpStatusCode.Unauthorized, "Invalid or missing token")
                return@get
            }
            call.respond(HttpStatusCode.OK, "Your userId is $userId")
        }
    }
}