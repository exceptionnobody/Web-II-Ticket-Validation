package com.group12.server

import com.group12.server.controller.TicketPayload
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import java.util.concurrent.atomic.AtomicInteger

// Server integration tests
@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class ServerApplicationTests {
	@LocalServerPort
	protected var port: Int = 0
	@Autowired
	lateinit var restTemplate: TestRestTemplate

	// Tests context loading
	@Test
	fun contextLoads() {
	}

	// Tests a valid ticket
	@Test
	fun acceptValidZoneAndTicket() {
		val baseUrl = "http://localhost:$port"
		val token = Utility.generateToken(0,10, "0123456789")
		val request = HttpEntity(TicketPayload("1", token))
		val response = restTemplate.postForEntity<Void>("$baseUrl/validate", request)
		assert(response.statusCode == HttpStatus.OK)
	}

	// Tests a ticket with an empty zone
	@Test
	fun rejectInvalidZone() {
		val baseUrl = "http://localhost:$port"
		val token = Utility.generateToken(1,10, "1")
		val request = HttpEntity(TicketPayload("", token))
		val response = restTemplate.postForEntity<Void>("$baseUrl/validate", request)
		assert(response.statusCode == HttpStatus.FORBIDDEN)
	}

	// Tests a ticket with wrong zones fields
	@Test
	fun rejectWrongZone() {
		val baseUrl = "http://localhost:$port"
		val token = Utility.generateToken(2,10, "1")
		val request = HttpEntity(TicketPayload("2", token))
		val response = restTemplate.postForEntity<Void>("$baseUrl/validate", request)
		assert(response.statusCode == HttpStatus.FORBIDDEN)
	}

	// Tests a ticket with an empty JWT
	@Test
	fun rejectInvalidToken() {
		val baseUrl = "http://localhost:$port"
		val token = ""
		val request = HttpEntity(TicketPayload("1", token))
		val response = restTemplate.postForEntity<Void>("$baseUrl/validate", request)
		assert(response.statusCode == HttpStatus.FORBIDDEN)
	}

	// Tests a ticket with a wrong JWT
	@Test
	fun rejectWrongToken() {
		val baseUrl = "http://localhost:$port"
		val token = Utility.generateToken(3,-10, "1") + "12345"
		val request = HttpEntity(TicketPayload("1", token))
		val response = restTemplate.postForEntity<Void>("$baseUrl/validate", request)
		assert(response.statusCode == HttpStatus.FORBIDDEN)
	}

	// Tests a ticket with an expired JWT
	@Test
	fun rejectExpiredToken() {
		val baseUrl = "http://localhost:$port"
		val token = Utility.generateToken(4,-10, "1")
		val request = HttpEntity(TicketPayload("1", token))
		val response = restTemplate.postForEntity<Void>("$baseUrl/validate", request)
		assert(response.statusCode == HttpStatus.FORBIDDEN)
	}

	//
	// Ticket usage tests
	//

	// Tests an already validated ticket
	@Test
	fun rejectValidatedToken() {
		val baseUrl = "http://localhost:$port"
		val token = Utility.generateToken(500,10, "1")
		val request = HttpEntity(TicketPayload("1", token))
		restTemplate.postForEntity<Void>("$baseUrl/validate", request)
		val response = restTemplate.postForEntity<Void>("$baseUrl/validate", request)
		assert(response.statusCode == HttpStatus.FORBIDDEN)
	}

	@Test
	fun rejectValidatedTokenMultipleThreads() {
		val baseUrl = "http://localhost:$port"
		val token = Utility.generateToken(500,10, "1")
		val count403 = AtomicInteger()
		val count  = AtomicInteger()
		val request = HttpEntity(TicketPayload("1", token))
		val tl = mutableListOf<Thread>()
		for (i in 1..16) {
			tl.add(Thread{
					val response = restTemplate.postForEntity<Void>("$baseUrl/validate", request)
				if (response.statusCode == HttpStatus.FORBIDDEN)
					count403.incrementAndGet()
				else
					count.incrementAndGet()
			})
		}
		tl.forEach { it.run() }
		tl.forEach { it.join() }
		Assertions.assertEquals(1,count.get())
		Assertions.assertEquals(15, count403.get())
	}
}
