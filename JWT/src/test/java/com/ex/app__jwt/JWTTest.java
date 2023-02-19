package com.ex.app__jwt;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JWTTest {

	@Value("${custom.jwt.secretKey}")
	private String secretKey;

	@Test
	@DisplayName("secretKey 존재 확인")
	void t1() {
		assertThat(secretKey).isNotNull();
	}

}
