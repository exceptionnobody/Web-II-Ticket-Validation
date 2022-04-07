package com.group12.server.service.impl

import com.group12.server.exception.ValidationException
import com.group12.server.service.TicketService
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.crypto.SecretKey

// Server services implementation
@Service
class TicketServiceImpl(private val secretKey: SecretKey) : TicketService {

    private val jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build()
    private val ticketMap = ConcurrentHashMap(mutableMapOf(400L to "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOjUwMCwiZXhwIjoxNjUwMDYwMDAwLCJ2eiI6IjEiLCJpYXQiOjE2NDg4MjY0NTl9.eMjGT8OhaIa14rCAv7OmnEF5ds2hHtaYOvWYFyjt-74"))

    // TODO: Please remove this test function when it becomes useless
    // Returns a new valid signed JWT
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


    // Validates a ticket
    // A ticket is valid if all the following conditions are true:
    // - JWT format is valid
    // - Zone or token fields are not empty
    // - JWT is not expired
    // - The zone provided is included in the JWT
    // - The ticket has never been validated before
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
            //check if the ticket is already validated
            //if you want to run the app without ticket validation check the comment the next 2 lines of code
            //beware if you comment those lines some test are gonna files because they check point 6
            if(ticketMap.putIfAbsent(ticket,token)!= null)
                throw  ValidationException()
        } catch (e: Exception) {
            throw ValidationException()
        }
    }
}