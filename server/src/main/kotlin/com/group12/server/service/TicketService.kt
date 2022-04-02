package com.group12.server.service

interface TicketService{
    fun generateTicket() : String
    fun validateTicket(zone: String, token: String)
}