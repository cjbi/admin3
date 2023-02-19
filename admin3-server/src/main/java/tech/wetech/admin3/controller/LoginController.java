package tech.wetech.admin3.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.wetech.admin3.sys.service.SessionService;
import tech.wetech.admin3.sys.service.dto.UserinfoDTO;

/**
 * @author cjbi
 */
@RestController
public class LoginController {

  private final SessionService sessionService;

  public LoginController(SessionService sessionService) {
    this.sessionService = sessionService;
  }

  @PostMapping("/login")
  private ResponseEntity<UserinfoDTO> login(@RequestBody @Valid LoginRequest request) {
    return ResponseEntity.ok(sessionService.login(request.username(), request.password()));
  }

  @SecurityRequirement(name = "bearerAuth")
  @PostMapping("/logout")
  public ResponseEntity<Void> logout(HttpServletRequest request) {
    String token = request.getHeader("Authorization").replace("Bearer", "").trim();
    sessionService.logout(token);
    return ResponseEntity.ok().build();
  }

  @SecurityRequirement(name = "bearerAuth")
  @GetMapping("/userinfo")
  public ResponseEntity<UserinfoDTO> userInfo(HttpServletRequest request) {
    String token = request.getHeader("Authorization").replace("Bearer", "").trim();
    return ResponseEntity.ok(sessionService.getLoginUserInfo(token));
  }

  record LoginRequest(@NotBlank String username, @NotBlank String password) {
  }


}
