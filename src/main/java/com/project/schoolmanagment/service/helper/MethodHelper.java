package com.project.schoolmanagment.service.helper;

import com.project.schoolmanagment.entity.concretes.user.User;
import com.project.schoolmanagment.exception.BadRequestException;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.messages.ErrorMessages;
import com.project.schoolmanagment.repository.user.UserRepository;
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

}
