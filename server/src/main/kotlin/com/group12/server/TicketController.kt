package com.group12.server

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

data class ValidatePayload (val zone: String, val token: String)

@RestController
class TicketController(val ticketService: TicketService) {

    // TODO: Please remove this test function when it becomes useless
    // Generate token
    @GetMapping("/generate")
    fun generate() : String {
        return ticketService.generateTicket()
    }

    // Validate token
    @PostMapping("/validate")
    fun validate(@RequestBody p: ValidatePayload) : ResponseEntity<Void> {
        try {
            ticketService.validateTicket(p.zone, p.token)
        } catch (e: ValidationException) {
            return ResponseEntity(HttpStatus.FORBIDDEN)
        }
        return ResponseEntity(HttpStatus.OK)
    }

}