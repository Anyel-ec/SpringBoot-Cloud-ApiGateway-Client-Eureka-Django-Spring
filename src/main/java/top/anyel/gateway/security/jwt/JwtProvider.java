package top.anyel.gateway.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import top.anyel.gateway.model.Users;
import top.anyel.gateway.security.config.UsuarioPrincipal;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

@Component
@Slf4j
public class JwtProvider {

  private static final String CLAIMROLES = "roles";

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private int expiration;

  public String generateToken(Authentication authentication) {
    UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
    List<String> roles = usuarioPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    return Jwts.builder()
        .subject(usuarioPrincipal.getUsername())
        .claim(CLAIMROLES, roles)
        .issuedAt(new Date())
        .expiration(new Date(new Date().getTime() + expiration * 1000))
        .signWith(getSecret(secret))
        .compact();
  }

  public String generateJwtByUsername(Users user) {
    UsuarioPrincipal usuarioPrincipal = UsuarioPrincipal.build(user);
    List<String> roles = usuarioPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    return Jwts.builder()
        .subject(user.getUsername())
        .claim(CLAIMROLES, roles)
        .issuedAt(new Date())
        .expiration(new Date(new Date().getTime() + expiration * 1000))
        .signWith(getSecret(secret))
        .compact();
  }

  public String getNombreUsuarioFromToken(String token) {
    Jws<Claims> parsed = Jwts.parser().verifyWith(getSecret(secret)).build().parseSignedClaims(token);
    return parsed.getPayload().getSubject();
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().verifyWith(getSecret(secret)).build().parseSignedClaims(token);
      return true;
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token");
    } catch (UnsupportedJwtException e) {
      log.error("Unsupported JWT token");
    } catch (ExpiredJwtException e) {
      log.error("Expired JWT token");
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty");
    } catch (SignatureException e) {
      log.error("Invalid JWT signature");
    } catch (JwtException e) {
      log.error("Invalid JWT");
    }
    return false;
  }

  private SecretKey getSecret(String secret) {
    byte[] secretBytes = Decoders.BASE64URL.decode(secret);
    return Keys.hmacShaKeyFor(secretBytes);
  }

}