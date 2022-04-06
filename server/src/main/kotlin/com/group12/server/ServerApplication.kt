package com.group12.server

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import javax.crypto.SecretKey

@SpringBootApplication
class ServerApplication() {
	// Key generated with:
	// val key = Keys.secretKeyFor(SignatureAlgorithm.HS256)
	// val stringKey = Encoders.BASE64.encode(key.encoded)
	// Stored and read from application.properties file
	@Value("\${key}")
	lateinit var stringKey: String

	// Returns a secret key
	@Bean
	fun secretKey(): SecretKey {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(stringKey))
	}
}

fun main(args: Array<String>) {
	runApplication<ServerApplication>(*args)
}
