package com.project.schoolmanagment.controller.user;

import com.project.schoolmanagment.payload.request.user.UserRequest;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.payload.response.user.UserResponse;
import com.project.schoolmanagment.service.user.UserService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
  
  public final UserService userService;
  
  @PostMapping("/save/{userRole}")
  public ResponseEntity<ResponseMessage<UserResponse>>saveUser(
      @Valid @RequestBody UserRequest userRequest,
      @PathVariable String userRole){
    return ResponseEntity.ok(userService.saveUser(userRequest,userRole));
  }

}
