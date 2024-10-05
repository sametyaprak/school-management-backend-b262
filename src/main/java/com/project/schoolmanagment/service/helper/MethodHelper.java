package com.project.schoolmanagment.service.helper;

import com.project.schoolmanagment.entity.concretes.user.User;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exception.BadRequestException;
import com.project.schoolmanagment.exception.ConflictException;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.messages.ErrorMessages;
import com.project.schoolmanagment.repository.user.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MethodHelper {
  
  private final UserRepository userRepository;
  
  public User isUserExist(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(()->
            new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE,userId)));
  }
  
  public void checkBuildIn(User user){
    if(user.getBuiltIn()){
      throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
    }
  }
  
  public User loadByUsername(String username) {
    User user =  userRepository.findByUsername(username);
    if(user == null){
      throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE,username));
    }
    return user;
  }
  
  public void checkIsAdvisor(User user){
    if(!user.getIsAdvisor()){
      throw new BadRequestException(String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE,user.getId()));
    }
  }
  
  public void checkRole(User user, RoleType roleType){
    if(!user.getUserRole().getRoleType().equals(roleType)){
      throw new ConflictException(ErrorMessages.NOT_HAVE_EXPECTED_ROLE_USER);
    }
  }
  
  public List<User>getUserList(List<Long>idList){
    return userRepository.findByIdList(idList);
  }

}
