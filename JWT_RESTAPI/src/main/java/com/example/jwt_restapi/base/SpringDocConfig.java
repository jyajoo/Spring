package com.example.jwt_restapi.base;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

  @Bean
  public OpenAPI springShopOpenAPI() {
    return new OpenAPI()
        .info(new Info().title("JWT_RESTAPI API")
            .description("JWT와 REST API를 실습한 API 명세서입니다.")
            .version("v0.0.1"));
  }
}
