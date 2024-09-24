package com.project.schoolmanagment.service.user;

import com.project.schoolmanagment.entity.concretes.user.User;
import com.project.schoolmanagment.payload.mappers.UserMapper;
import com.project.schoolmanagment.payload.messages.SuccessMessages;
import com.project.schoolmanagment.payload.request.abstracts.BaseUserRequest;
import com.project.schoolmanagment.payload.request.user.UserRequest;
import com.project.schoolmanagment.payload.response.abstracts.AbstractUserResponse;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.payload.response.user.UserResponse;
import com.project.schoolmanagment.repository.user.UserRepository;
import com.project.schoolmanagment.service.helper.MethodHelper;
import com.project.schoolmanagment.service.helper.PageableHelper;
import com.project.schoolmanagment.service.validator.UniquePropertyValidator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  
  private final UserRepository userRepository;
  private final UniquePropertyValidator uniquePropertyValidator;
  private final UserMapper userMapper;
  private final PageableHelper pageableHelper;
  private final MethodHelper methodHelper;

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
        .message(SuccessMessages.USER_CREATE)
        .returnBody(userMapper.mapUserToUserResponse(savedUser))
        .build();
  }

  public Page<UserResponse> getUserByPage(int page, int size, String sort, String type,
      String userRole) {
    Pageable pageable = pageableHelper.getPageable(page, size, sort, type);
    return userRepository.findByUserByRole(userRole,pageable)
        .map(userMapper::mapUserToUserResponse);    
  }

  public List<UserResponse> getUserByName(String username) {
    return userRepository.findByUsernameContainingIgnoreCase(username)
        .stream()
        .map(userMapper::mapUserToUserResponse)
        .collect(Collectors.toList());    
  }

  public ResponseMessage<UserResponse> getUserById(Long userId) {
    //validate if id exist
    User user = methodHelper.isUserExist(userId);
    return ResponseMessage.<UserResponse>builder()
        .message(SuccessMessages.USER_FOUND)
        .returnBody(userMapper.mapUserToUserResponse(user))
        .httpStatus(HttpStatus.OK)
        .build();
  }

  public String deleteById(Long id) {
    methodHelper.isUserExist(id);
    userRepository.deleteById(id);
    return SuccessMessages.USER_DELETE;
  }

  public ResponseMessage<AbstractUserResponse> updateUserById(UserRequest userRequest,
      Long userId) {
    //validate is user exist
    User user = methodHelper.isUserExist(userId);
    //build in users cannot be updated
    methodHelper.checkBuildIn(user);
    //validate unique properties
    
  }
}
