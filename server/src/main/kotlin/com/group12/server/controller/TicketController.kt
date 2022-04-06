package com.group12.server.controller

import com.group12.server.exception.ValidationException
import com.group12.server.service.TicketService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

// Ticket DTO
data class TicketPayload(val zone: String, val token: String)

// Endpoint controller
@RestController
class TicketController(val ticketService: TicketService) {

    // TODO: Please remove this test function when it becomes useless
    // Generates a valid JWT
    @GetMapping("/generate")
    fun generate() : String {
        return ticketService.generateTicket()
    }

    // Validates a ticket
    @PostMapping("/validate")
    fun validate(@RequestBody p: TicketPayload) : ResponseEntity<Unit> {
        try {
            ticketService.validateTicket(p.zone, p.token)
        } catch (e: ValidationException) {
            return ResponseEntity(HttpStatus.FORBIDDEN)
        }
        return ResponseEntity(HttpStatus.OK)
    }

}