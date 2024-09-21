package com.project.schoolmanagment.payload.mappers;

import com.project.schoolmanagment.entity.concretes.user.User;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.payload.request.user.UserRequest;
import com.project.schoolmanagment.service.user.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
  
  private final UserRoleService userRoleService;
  
  public User mapUserRequestToUser(UserRequest userRequest, String userRole) {
    User user = User.builder()
        .username(userRequest.getUsername())
        .name(userRequest.getName())
        .surname(userRequest.getSurname())
        .password(userRequest.getPassword())
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
    }
  }

}
