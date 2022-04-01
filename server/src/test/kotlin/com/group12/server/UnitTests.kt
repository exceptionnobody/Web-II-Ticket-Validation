package com.group12.server

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UnitTests() {
    @Autowired
    lateinit var ticketService: TicketService

    @Test
    fun acceptValidJWT() {
        Assertions.assertDoesNotThrow() {
            val zone = "1"
            val token = Utility.generateToken(0,10, "0123456789")
            ticketService.validateTicket(zone, token)
        }
    }

    @Test
    fun rejectInvalidJWT_ValidityZoneNotIncluded() {
        Assertions.assertThrows(ValidationException::class.java) {
            val zone = "2"
            val token = Utility.generateToken(1,10, "1")
            ticketService.validateTicket(zone, token)
        }
    }

    @Test
    fun rejectInvalidJWT_Expired() {
        Assertions.assertThrows(ValidationException::class.java) {
            val zone = "1"
            val token = Utility.generateToken(2,-10, "1")
            ticketService.validateTicket(zone, token)
        }
    }

    @Test
    fun rejectInvalidJWT_WrongFormat() {
        Assertions.assertThrows(ValidationException::class.java) {
            val zone = "1"
            val token = Utility.generateToken(3,10, "1") + "12345"
            ticketService.validateTicket(zone, token)
        }
    }

    @Test
    fun rejectInvalidJWT_EmptyZone() {
        Assertions.assertThrows(ValidationException::class.java) {
            val zone = ""
            val token = Utility.generateToken(4,10, "1")
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
    @Test
    fun rejectJWTAlreadyValidated() {
        val zone = "1"
        val validToken = Utility.generateToken(500,10, "1")
        ticketService.validateTicket(zone, validToken)
        Assertions.assertThrows(ValidationException::class.java) {
            val token = Utility.generateToken(500,10, "1")
            ticketService.validateTicket(zone, token)
        }
    }
}