package top.anyel.gateway.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.anyel.gateway.dto.JsonResponse;

@RestController
public class LoginController {
    @PostMapping("/login")
    public ResponseEntity<JsonResponse> login(HttpServletRequest request, @Valid @RequestBody(required = true) LoginUser login) {
        JsonResponse res = usuarioService.loginCustom(login);
        String dataOut = res.getRespuesta() ? "Login successfully" : res.toString();
        auditoriaService.saveAuditoriaInternaController(null, request, login.getUsername(), dataOut, "LOGIN", res.getRespuesta(), login.getUsername());
        return ResponseEntity.status(res.getCodeStatus()).body(res);
    }
}
