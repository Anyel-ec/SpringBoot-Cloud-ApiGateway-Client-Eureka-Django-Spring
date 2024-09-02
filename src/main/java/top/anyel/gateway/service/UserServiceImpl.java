package top.anyel.gateway.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.anyel.gateway.dto.JsonResponse;
import top.anyel.gateway.dto.LoginUser;
import top.anyel.gateway.model.Users;
import top.anyel.gateway.repository.UsersRepository;
import top.anyel.gateway.security.jwt.JwtProvider;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UserServiceImpl {

  @Autowired
  UsersRepository userRepository;

  @Autowired
  JwtProvider jwtProvider;

  @Autowired
  PasswordEncoder encoder;

  @Value("${msg.no.autorizado}")
  private String noAutorizado;

  public JsonResponse loginCustom(LoginUser login) {
    try {
      log.info("loginCustom: " + login.getUsername());
      String username = login.getUsername() != null ? login.getUsername().trim() : "";
      String pass = login.getPassword() != null ? login.getPassword().trim() : "";

      if (username.isEmpty() || pass.isEmpty()) {
        return new JsonResponse(false, HttpStatus.BAD_REQUEST.value(), "Parámetros inválidos.");
      }

      Users user = userRepository.obtenerPorUsername(username);
      if (user == null) {
        return new JsonResponse(false, HttpStatus.BAD_REQUEST.value(), "Usuario incorrecto.");
      }

      if (!encoder.matches(pass, user.getPassword())) {
        return new JsonResponse(false, HttpStatus.BAD_REQUEST.value(), "Password incorrecto.");
      }
      String jwt = jwtProvider.generateJwtByUsername(user);
      return new JsonResponse(true, HttpStatus.OK.value(), jwt);
    } catch (Exception e) {
      log.error("Catch loginCustom: " + e.getMessage());
      e.printStackTrace();
      return new JsonResponse(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error login", null, e.getMessage());
    }
  }

  public JsonResponse validateUserJWT(String token) {
    try {
      String authHeader = token.replace("Bearer ", "");
      Boolean isValid = jwtProvider.validateToken(authHeader);

      if (!isValid) {
        log.error("El token es incorrecto - validateUserJWT");
        return new JsonResponse(false, HttpStatus.UNAUTHORIZED.value(), noAutorizado);
      }

      String username = SecurityContextHolder.getContext().getAuthentication().getName();
      Users user = userRepository.obtenerPorUsername(username);

      if (user == null) {
        log.error("Usuario no encontrado - validateUserJWT");
        return new JsonResponse(false, HttpStatus.UNAUTHORIZED.value(), noAutorizado);
      }

      return new JsonResponse(true, HttpStatus.OK.value(), "Autorizado", user);
    } catch (Exception e) {
      log.error("Catch validateUserJWT " + e.getMessage());
      e.printStackTrace();
      return new JsonResponse(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al validar usuario", null, e.getMessage());
    }
  }
}
