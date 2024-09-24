package com.project.schoolmanagment.service.helper;

import com.project.schoolmanagment.entity.concretes.user.User;
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
        .orElseThrow(()->new ResourceNotFoundException(ErrorMessages.))
  }

}
