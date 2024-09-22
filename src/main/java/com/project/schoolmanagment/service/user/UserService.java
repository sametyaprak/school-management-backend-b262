package com.project.schoolmanagment.service.user;

import com.project.schoolmanagment.entity.concretes.user.User;
import com.project.schoolmanagment.payload.mappers.UserMapper;
import com.project.schoolmanagment.payload.request.user.UserRequest;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.payload.response.user.UserResponse;
import com.project.schoolmanagment.repository.user.UserRepository;
import com.project.schoolmanagment.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  
  private final UserRepository userRepository;
  private final UniquePropertyValidator uniquePropertyValidator;
  private final UserMapper userMapper;

  public ResponseMessage<UserResponse> saveUser(UserRequest userRequest, String userRole) {
    //validate unique properties
    uniquePropertyValidator.checkDuplicate(
        userRequest.getUsername(),
        userRequest.getSsn(),
        userRequest.getPhoneNumber(),
        userRequest.getEmail());
    //map DTO -> entity
    User userToSave = userMapper.mapUserRequestToUser(userRequest, userRole);
    //save operation
    User savedUser = userRepository.save(userToSave);
    //map entity to DTO and return
    return ResponseMessage.<UserResponse>builder()
        .message()
  }
}
