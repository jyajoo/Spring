package com.ex.app__jwt;

import static org.assertj.core.api.Assertions.assertThat;

import com.ex.app__jwt.jwt.JwtProvider;
import io.jsonwebtoken.security.Keys;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@SpringBootTest
class JWTTest {

	@Value("${custom.jwt.secretKey}")
	private String plainSecretKey;

	@Autowired
	private JwtProvider jwtProvider;

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

	@Test
	@DisplayName("JwtProvider 객체로 SecretKey 객체 생성")
	void t3() {
		SecretKey secretKey = TestUtil.callMethod(jwtProvider, "getSecretKey");
		assertThat(secretKey).isNotNull();
	}

	@Test
	@DisplayName("SecretKey 객체는 하나뿐이다.")
	void t4() {
		SecretKey secretKey1 = TestUtil.callMethod(jwtProvider, "getSecretKey");
		SecretKey secretKey2 = TestUtil.callMethod(jwtProvider, "getSecretKey");

		assertThat(secretKey1).isSameAs(secretKey2);
	}

	@Test
	@DisplayName("accessToken 얻기")
	void t5() {
		// 회원 번호 : 1, username : admin, role : MEMBER & ADMIN
		Map<String, Object> claims = new HashMap<>();
		claims.put("id", 1);
		claims.put("usename", "admin");
		claims.put("authorities", Arrays.asList(
				new SimpleGrantedAuthority("MEMBER"),
				new SimpleGrantedAuthority("ADMIN")
		));

		String accessToken = jwtProvider.generateAccessToken(claims, 60 * 60 * 5);
		System.out.println(accessToken);
		assertThat(accessToken).isNotNull();
	}
}
