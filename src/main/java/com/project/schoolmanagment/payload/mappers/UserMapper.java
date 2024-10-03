package com.project.schoolmanagment.payload.mappers;

import com.project.schoolmanagment.entity.concretes.user.User;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.messages.ErrorMessages;
import com.project.schoolmanagment.payload.request.abstracts.BaseUserRequest;
import com.project.schoolmanagment.payload.request.user.StudentUpdateRequest;
import com.project.schoolmanagment.payload.request.user.UserRequest;
import com.project.schoolmanagment.payload.response.user.StudentResponse;
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

  public StudentResponse mapUserToStudentResponse(User student){
    return StudentResponse.builder()
        .id(student.getId())
        .username(student.getUsername())
        .name(student.getName())
        .surname(student.getSurname())
        .birthday(student.getBirthday())
        .ssn(student.getSsn())
        .birthPlace(student.getBirthplace())
        .phoneNumber(student.getPhoneNumber())
        .gender(student.getGender())
        .email(student.getEmail())
        .studentNumber(student.getStudentNumber())
        .motherName(student.getMotherName())
        .fatherName(student.getFatherName())
        .lessonProgramSet(student.getLessonProgramList())
        .isActive(student.isActive())
        .build();
  }

  public User mapStudentUpdateRequestToUser(StudentUpdateRequest studentUpdateRequest){
    return User.builder()
        .username(studentUpdateRequest.getUsername())
        .name(studentUpdateRequest.getName())
        .ssn(studentUpdateRequest.getSsn())
        .surname(studentUpdateRequest.getSurname())
        .birthday(studentUpdateRequest.getBirthDay())
        .birthplace(studentUpdateRequest.getBirthPlace())
        .phoneNumber(studentUpdateRequest.getPhoneNumber())
        .gender(studentUpdateRequest.getGender())
        .email(studentUpdateRequest.getEmail())
        .fatherName(studentUpdateRequest.getFatherName())
        .motherName(studentUpdateRequest.getMotherName())
        .build();
  }

}
