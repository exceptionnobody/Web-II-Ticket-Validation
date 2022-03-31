package com.group12.server

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

class ValidationException : Exception()

interface TicketServiceInterface {
    fun generateTicket() : String
    fun validateTicket(zone: String, token: String)
}

@Service
class TicketService(private val secretKey: SecretKey) : TicketServiceInterface {

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
        val claims = mapOf<String,Any>("sub" to 500, "exp" to date.time,"vz" to "1", "iat" to now.time)
        return Jwts.builder().setClaims(claims).signWith(secretKey).compact()
    }

    override fun validateTicket(zone: String, token: String) {
        try {
            if (zone.trim().isEmpty() ||
                token.trim().isEmpty() )
                throw ValidationException()
            val jws = jwtParser.parseClaimsJws(token)
            val now = Calendar.getInstance().time
            val vz : String = jws.body["vz"] as String
            if (now > jws.body.expiration ||
                !vz.contains(zone))
                throw ValidationException()
        } catch (e: JwtException) {
            throw ValidationException()
        }
    }
}