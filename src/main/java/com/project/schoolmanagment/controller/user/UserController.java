package com.project.schoolmanagment.controller.user;

import com.project.schoolmanagment.payload.request.user.UserRequest;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.payload.response.user.UserResponse;
import com.project.schoolmanagment.service.user.UserService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  
  @GetMapping("/getUserByPage/{userRole}")
  public ResponseEntity<Page<UserResponse>> getUserByPage(
      @PathVariable String userRole,
      @RequestParam(value = "page",defaultValue = "0") int page,
      @RequestParam(value = "size",defaultValue = "10") int size,
      @RequestParam(value = "sort",defaultValue = "name") String sort,
      @RequestParam(value = "type",defaultValue = "desc") String type){
    Page<UserResponse>userResponses = userService.getUserByPage(page,size,sort,type,userRole);
    return ResponseEntity.ok(userResponses);
  }

  @GetMapping("/getUserByName")
  public List<UserResponse>getUserByName(@RequestParam (name = "name") String username){
    return userService.getUserByName(username);
  }

  @GetMapping("/getUserById/{userId}")
  public List<UserResponse>getUserById(@PathVariable String userId){
    return userService.getUserById(userId);
  }
  
  
  
  
  
  
  

}
