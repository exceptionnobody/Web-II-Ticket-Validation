package com.group12.server.service

// Server services definition
interface TicketService{
    fun validateTicket(zone: String, token: String)
}