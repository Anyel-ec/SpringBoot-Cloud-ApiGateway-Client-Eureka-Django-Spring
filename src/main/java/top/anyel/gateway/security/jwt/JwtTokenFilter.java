package top.anyel.gateway.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.anyel.gateway.security.config.UserDetailsServiceImpl;

import java.io.IOException;

@Component
@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

  @Autowired
  JwtProvider jwtProvider;

  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
    try {
      String token = getToken(req);
      if (token != null && jwtProvider.validateToken(token)) {
        String username = jwtProvider.getNombreUsuarioFromToken(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    } catch (Exception e) {
      log.error("Can NOT set user authentication {}", e.getMessage());
    }
    filterChain.doFilter(req, res);
  }

  private String getToken(HttpServletRequest request) {
    String header = request.getHeader("Authorization");
    if (header != null && header.startsWith("Bearer")) {
      return header.replace("Bearer ", "");
    }
    return null;
  }
}