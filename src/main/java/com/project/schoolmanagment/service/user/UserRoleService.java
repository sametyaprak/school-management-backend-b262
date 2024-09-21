package com.project.schoolmanagment.service.user;

import com.project.schoolmanagment.entity.concretes.user.UserRole;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.messages.ErrorMessages;
import com.project.schoolmanagment.repository.user.UserRoleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleService {

  
  private final UserRoleRepository userRoleRepository;
  
  public UserRole getUserRole(RoleType roleType){
    return userRoleRepository.findByEnumRoleEquals(roleType)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ROLE_NOT_FOUND));
  }
  
  public List<UserRole> getAllUserRoles(){
    return userRoleRepository.findAll();
  }
  
}
