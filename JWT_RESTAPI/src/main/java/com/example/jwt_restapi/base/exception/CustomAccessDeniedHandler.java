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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {
    MemberException e = new MemberException(ErrorCode.ACCESS_DENIED);
    response.setStatus(e.getErrorCode().getStatus());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE+ ";charset=UTF-8");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(objectMapper.writeValueAsString(
        RsData.of(ResultCode.FAIL, e.getErrorCode().getMsg(), null)));
  }
}
