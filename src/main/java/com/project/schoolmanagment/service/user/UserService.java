package com.project.schoolmanagment.service.user;

import com.project.schoolmanagment.payload.request.user.UserRequest;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.payload.response.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  public ResponseMessage<UserResponse> saveUser(UserRequest userRequest, String userRole) {
    //validate unique properties
    
  }
}
