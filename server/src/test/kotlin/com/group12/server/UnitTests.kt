package com.group12.server

import io.jsonwebtoken.Jwts
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import javax.crypto.SecretKey

@SpringBootTest
class UnitTests() {
    @Autowired
    lateinit var ticketService: TicketService
    @Autowired
    lateinit var secretKey: SecretKey

    fun generateToken(offsetDays: Int, vz: String): String {
        val date = Calendar.getInstance()
        date.add(Calendar.DATE, offsetDays)
        val claims = mapOf<String,Any>(
            "sub" to "token",
            "exp" to date.time,
            "vz" to vz,
            "iat" to Calendar.getInstance().time
        )
        return Jwts.builder().setClaims(claims).signWith(secretKey).compact()
    }

    @Test
    fun acceptValidJWT() {
        Assertions.assertDoesNotThrow() {
            val zone = "1"
            val token = generateToken(10, "0123456789")
            ticketService.validateTicket(zone, token)
        }
    }

    @Test
    fun rejectInvalidJWT_ValidityZoneNotIncluded() {
        Assertions.assertThrows(ValidationException::class.java) {
            val zone = "2"
            val token = generateToken(10, "1")
            ticketService.validateTicket(zone, token)
        }
    }

    @Test
    fun rejectInvalidJWT_Expired() {
        Assertions.assertThrows(ValidationException::class.java) {
            val zone = "1"
            val token = generateToken(-10, "1")
            ticketService.validateTicket(zone, token)
        }
    }

    @Test
    fun rejectInvalidJWT_WrongFormat() {
        Assertions.assertThrows(ValidationException::class.java) {
            val zone = "1"
            val token = generateToken(10, "1") + "12345"
            ticketService.validateTicket(zone, token)
        }
    }

    @Test
    fun rejectInvalidJWT_EmptyZone() {
        Assertions.assertThrows(ValidationException::class.java) {
            val zone = ""
            val token = generateToken(10, "1")
            ticketService.validateTicket(zone, token)
        }
    }

    @Test
    fun rejectInvalidJWT_EmptyToken() {
        Assertions.assertThrows(ValidationException::class.java) {
            val zone = "1"
            val token = ""
            ticketService.validateTicket(zone, token)
        }
    }
}