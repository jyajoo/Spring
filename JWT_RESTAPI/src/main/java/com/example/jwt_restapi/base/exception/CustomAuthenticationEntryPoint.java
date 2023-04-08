package com.example.jwt_restapi.base.exception;

import com.example.jwt_restapi.base.dto.ResultCode;
import com.example.jwt_restapi.base.dto.RsData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * 인증 오류 (401,
 */
@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    MemberException e = new MemberException(ErrorCode.INVALID_TOKEN);
    response.setStatus(e.getErrorCode().getStatus());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE+ ";charset=UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(objectMapper.writeValueAsString(
        RsData.of(ResultCode.FAIL, e.getErrorCode().getMsg(), null)));
  }
}
