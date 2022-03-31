package com.group12.server

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import io.jsonwebtoken.Jwts
import org.springframework.http.HttpStatus
import java.util.*
import javax.crypto.SecretKey

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class ServerApplicationTests() {
	@LocalServerPort
	protected var port: Int = 0
	@Autowired
	lateinit var restTemplate: TestRestTemplate
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
	fun contextLoads() {
	}

	@Test
	fun acceptValidZoneAndTicket() {
		val baseUrl = "http://localhost:$port"
		val token = generateToken(10, "0123456789")
		val request = HttpEntity(ValidatePayload("1", token))
		val response = restTemplate.postForEntity<Void>("$baseUrl/validate", request)
		assert(response.statusCode == HttpStatus.OK)
	}

	@Test
	fun rejectInvalidZone() {
		val baseUrl = "http://localhost:$port"
		val token = generateToken(10, "1")
		val request = HttpEntity(ValidatePayload("", token))
		val response = restTemplate.postForEntity<Void>("$baseUrl/validate", request)
		assert(response.statusCode == HttpStatus.FORBIDDEN)
	}

	@Test
	fun rejectWrongZone() {
		val baseUrl = "http://localhost:$port"
		val token = generateToken(10, "1")
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
		val token = generateToken(-10, "1") + "12345"
		val request = HttpEntity(ValidatePayload("1", token))
		val response = restTemplate.postForEntity<Void>("$baseUrl/validate", request)
		assert(response.statusCode == HttpStatus.FORBIDDEN)
	}

	@Test
	fun rejectExpiredToken() {
		val baseUrl = "http://localhost:$port"
		val token = generateToken(-10, "1")
		val request = HttpEntity(ValidatePayload("1", token))
		val response = restTemplate.postForEntity<Void>("$baseUrl/validate", request)
		assert(response.statusCode == HttpStatus.FORBIDDEN)
	}

}
