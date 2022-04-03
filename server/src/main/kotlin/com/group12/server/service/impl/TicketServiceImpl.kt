package com.group12.server.service.impl

import com.group12.server.exception.ValidationException
import com.group12.server.service.TicketService
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import javax.crypto.SecretKey

@Service
class TicketServiceImpl(private val secretKey: SecretKey) : TicketService {

    private val jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build()
    private val ticketMap = ConcurrentHashMap(mutableMapOf(400L to "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOjUwMCwiZXhwIjoxNjUwMDYwMDAwLCJ2eiI6IjEiLCJpYXQiOjE2NDg4MjY0NTl9.eMjGT8OhaIa14rCAv7OmnEF5ds2hHtaYOvWYFyjt-74"))

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

    @Synchronized
    private fun checkIfValidated(ticket : Long,token : String) : Boolean{
        if(ticketMap.containsKey(ticket))
           return true
        else {
            ticketMap[ticket] = token
            return false
        }
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
                !vz.trim().contains(zone.trim()) || checkIfValidated(ticket,token))
                throw ValidationException()
        } catch (e: Exception) {
            throw ValidationException()
        }
    }
}