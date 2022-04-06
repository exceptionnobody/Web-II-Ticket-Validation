package com.group12.server.service

// Server services definition
interface TicketService{
    fun generateTicket() : String
    fun validateTicket(zone: String, token: String)
}