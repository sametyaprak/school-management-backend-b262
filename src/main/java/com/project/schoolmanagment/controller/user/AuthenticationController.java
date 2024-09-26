package com.project.schoolmanagment.controller.user;

import com.project.schoolmanagment.payload.request.authentication.LoginRequest;
import com.project.schoolmanagment.payload.response.authentication.LoginResponse;
import com.project.schoolmanagment.service.user.AuthenticationService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  
  private final AuthenticationService authenticationService;
  
  @PostMapping("/login")
  public ResponseEntity<LoginResponse>authenticateUser(
      @RequestBody @Valid LoginRequest loginRequest) {
    return ResponseEntity.ok(authenticationService.authenticateUser(loginRequest));
  }

}
