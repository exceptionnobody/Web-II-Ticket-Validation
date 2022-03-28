package com.group12.server

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

class ValidationException : Exception()

interface TicketServiceInterface {
    fun generateTicket() : String
    fun validateTicket(zone: Char, token: String)
}

@Service
class TicketService : TicketServiceInterface {

    // Key generated with:
    // val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
    // val stringKey = Encoders.BASE64.encode(key.encoded)
    private val stringKey = "LHoT7nKj0gb7M7TFAnZFxHzJVa1yOMUfVUaRAEB11pU="
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(stringKey))
    private val jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build()

    // TODO: Please remove this test function when it becomes useless
    override fun generateTicket() : String {
        val now = Calendar.getInstance()
        val date = Calendar.getInstance()
        date.add(Calendar.DATE,15)
        date.set(Calendar.HOUR_OF_DAY,0)
        date.set(Calendar.MINUTE,0)
        date.set(Calendar.MILLISECOND,0)
        date.set(Calendar.SECOND,0)
        val claims = mapOf<String,Any>("sub" to 500, "exp" to date.time,"vz" to "12", "iat" to now.time)
        return Jwts.builder().setClaims(claims).signWith(secretKey).compact()
    }

    override fun validateTicket(zone: Char, token: String) {
        try {
            val jws = jwtParser.parseClaimsJws(token)
            val now = Calendar.getInstance().time
            val vz : String = jws.body["vz"] as String
            if (now > jws.body.expiration || !vz.contains(zone))
                throw ValidationException()
        } catch (e: JwtException) {
            throw ValidationException()
        }
    }
}