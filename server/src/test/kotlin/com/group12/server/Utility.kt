package com.group12.server

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import java.util.*

// Contains utility properties and functions for tests
class Utility private constructor() {
    companion object {
        private const val stringKey = "LHoT7nKj0gb7M7TFAnZFxHzJVa1yOMUfVUaRAEB11pU="
        private val secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(stringKey))

        // Generates and returns a signed JWT
        fun generateToken(ticketId: Long, offsetDays: Int, vz: String): String {
            val date = Calendar.getInstance()
            date.add(Calendar.DATE, offsetDays)
            val claims = mapOf<String,Any>(
                    "sub" to ticketId,
                    "exp" to date.time,
                    "vz" to vz,
                    "iat" to Calendar.getInstance().time
            )
            return Jwts.builder().setClaims(claims).signWith(secretKey).compact()
        }
    }
}