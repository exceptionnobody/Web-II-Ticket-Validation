package com.group12.server

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class ServerApplicationTests() {
	@LocalServerPort
	protected var port: Int = 0
	@Autowired
	lateinit var restTemplate: TestRestTemplate

	@Test
	fun contextLoads() {
	}

	@Test
	fun acceptValidZoneAndTicket() {
		val baseUrl = "http://localhost:$port"
		val token = Utility.generateToken(0,10, "0123456789")
		val request = HttpEntity(ValidatePayload("1", token))
		val response = restTemplate.postForEntity<Void>("$baseUrl/validate", request)
		assert(response.statusCode == HttpStatus.OK)
	}

	@Test
	fun rejectInvalidZone() {
		val baseUrl = "http://localhost:$port"
		val token = Utility.generateToken(1,10, "1")
		val request = HttpEntity(ValidatePayload("", token))
		val response = restTemplate.postForEntity<Void>("$baseUrl/validate", request)
		assert(response.statusCode == HttpStatus.FORBIDDEN)
	}

	@Test
	fun rejectWrongZone() {
		val baseUrl = "http://localhost:$port"
		val token = Utility.generateToken(2,10, "1")
		val request = HttpEntity(ValidatePayload("2", token))
		val response = restTemplate.postForEntity<Void>("$baseUrl/validate", request)
		assert(response.statusCode == HttpStatus.FORBIDDEN)
	}

	@Test
	fun rejectInvalidToken() {
		val baseUrl = "http://localhost:$port"
		val token = ""
		val request = HttpEntity(ValidatePayload("1", token))
		val response = restTemplate.postForEntity<Void>("$baseUrl/validate", request)
		assert(response.statusCode == HttpStatus.FORBIDDEN)
	}

	@Test
	fun rejectWrongToken() {
		val baseUrl = "http://localhost:$port"
		val token = Utility.generateToken(3,-10, "1") + "12345"
		val request = HttpEntity(ValidatePayload("1", token))
		val response = restTemplate.postForEntity<Void>("$baseUrl/validate", request)
		assert(response.statusCode == HttpStatus.FORBIDDEN)
	}

	@Test
	fun rejectExpiredToken() {
		val baseUrl = "http://localhost:$port"
		val token = Utility.generateToken(4,-10, "1")
		val request = HttpEntity(ValidatePayload("1", token))
		val response = restTemplate.postForEntity<Void>("$baseUrl/validate", request)
		assert(response.statusCode == HttpStatus.FORBIDDEN)
	}

}
