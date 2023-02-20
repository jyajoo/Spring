package com.ex.app__jwt;

import static org.assertj.core.api.Assertions.assertThat;

import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JWTTest {

	@Value("${custom.jwt.secretKey}")
	private String plainSecretKey;

	@Test
	@DisplayName("secretKey 존재 확인")
	void t1() {
		assertThat(plainSecretKey).isNotNull();
	}

	@Test
	@DisplayName("secretKey 원문 => SecretKey 객체 생성")
	void t2() {
		/*
		Base64로 secretKey의 원문 인코딩
		HMAC-SHA 알고리즘을 이용하여 SecretKey 객체 생성
		 */
		String encodedKey = Base64.getEncoder().encodeToString(plainSecretKey.getBytes());
		SecretKey secretKey = Keys.hmacShaKeyFor(encodedKey.getBytes());

		assertThat(secretKey).isNotNull();
	}
}
