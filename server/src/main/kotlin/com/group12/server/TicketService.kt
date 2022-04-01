package com.group12.server

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.crypto.SecretKey

class ValidationException : Exception()

interface TicketServiceInterface {
    fun generateTicket() : String
    fun validateTicket(zone: String, token: String)
}

@Service
class TicketService(private val secretKey: SecretKey) : TicketServiceInterface {

    private val jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build()
    private val ticketMap = ConcurrentHashMap<Long,String>(mutableMapOf(400L to "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOjUwMCwiZXhwIjoxNjUwMDYwMDAwLCJ2eiI6IjEiLCJpYXQiOjE2NDg4MjY0NTl9.eMjGT8OhaIa14rCAv7OmnEF5ds2hHtaYOvWYFyjt-74"))
    // TODO: Please remove this test function when it becomes useless
    override fun generateTicket() : String {
        val now = Calendar.getInstance()
        val date = Calendar.getInstance()
        date.add(Calendar.DATE,15)
        date.set(Calendar.HOUR_OF_DAY,0)
        date.set(Calendar.MINUTE,0)
        date.set(Calendar.MILLISECOND,0)
        date.set(Calendar.SECOND,0)
        val claims = mapOf<String,Any>("sub" to 500L, "exp" to date.time,"vz" to "1", "iat" to now.time)
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
            val ticket = jws.body.subject.toLong()
            if (now > jws.body.expiration ||
                !vz.trim().contains(zone.trim()))
                throw ValidationException()
            if(ticketMap.containsKey(ticket))
                throw ValidationException()
            ticketMap[ticket]=token
        } catch (e: Exception) {
            throw ValidationException()
        }
    }
}