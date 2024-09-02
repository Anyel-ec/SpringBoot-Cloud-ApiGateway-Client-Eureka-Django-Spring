package top.anyel.gateway.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import top.anyel.gateway.dto.JsonResponse;

import java.io.IOException;

@Component
@Slf4j
public class JwtEntryPoint implements AuthenticationEntryPoint {

  @Value("${msg.no.autorizado}")
  private String noAutorizado;

  @Override
  public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e)
      throws IOException, ServletException {
    log.error("Unauthorized error. {}", e.getMessage());
    JsonResponse resp = new JsonResponse(Boolean.FALSE, HttpStatus.UNAUTHORIZED.value(), noAutorizado);
    res.setContentType("application/json");
    res.setStatus(HttpStatus.UNAUTHORIZED.value());
    res.getWriter().write(new ObjectMapper().writeValueAsString(resp));
    res.getWriter().flush();
    res.getWriter().close();
  }

}