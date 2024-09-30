package com.project.schoolmanagment.payload.mappers;

import com.project.schoolmanagment.entity.concretes.user.User;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.messages.ErrorMessages;
import com.project.schoolmanagment.payload.request.abstracts.BaseUserRequest;
import com.project.schoolmanagment.payload.request.user.UserRequest;
import com.project.schoolmanagment.payload.response.user.UserResponse;
import com.project.schoolmanagment.service.user.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
  
  private final UserRoleService userRoleService;
  
  private final PasswordEncoder passwordEncoder;
  
  public User mapUserRequestToUser(BaseUserRequest userRequest, String userRole) {
    User user = User.builder()
        .username(userRequest.getUsername())
        .name(userRequest.getName())
        .surname(userRequest.getSurname())
        .password(passwordEncoder.encode(userRequest.getPassword()))
        .ssn(userRequest.getSsn())
        .birthday(userRequest.getBirthDay())
        .phoneNumber(userRequest.getPhoneNumber())
        .gender(userRequest.getGender())
        .email(userRequest.getEmail())
        .builtIn(userRequest.getBuildIn())
        .isAdvisor(false)
        .build();
    //role handling with switch-case
    switch (userRole.toLowerCase()) {
      case "admin":
        if("Admin".equals(userRequest.getUsername())) {
          user.setBuiltIn(true);
        }
        user.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));
        break;
      case "dean":
        user.setUserRole(userRoleService.getUserRole(RoleType.MANAGER));
        break;
      case "vicedean":
        user.setUserRole(userRoleService.getUserRole(RoleType.ASSISTANT_MANAGER));
        break;
      case "student":
        user.setUserRole(userRoleService.getUserRole(RoleType.STUDENT));
        break;
      case "teacher":
        user.setUserRole(userRoleService.getUserRole(RoleType.TEACHER));
        break;
      default:
        throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_USER_ROLE_MESSAGE,userRole));
    }
    return user;
  }
  
  
  public UserResponse mapUserToUserResponse(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .username(user.getUsername())
        .name(user.getName())
        .surname(user.getSurname())
        .phoneNumber(user.getPhoneNumber())
        .gender(user.getGender())
        .birthday(user.getBirthday())
        .birthPlace(user.getBirthplace())
        .ssn(user.getSsn())
        .email(user.getEmail())
        .userRole(user.getUserRole().getRoleType().name())
        .build();        
  }

}
