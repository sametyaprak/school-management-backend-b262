package com.project.schoolmanagment.service.validator;

import com.project.schoolmanagment.exception.ConfictException;
import com.project.schoolmanagment.payload.messages.ErrorMessages;
import com.project.schoolmanagment.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {
  
  private final UserRepository userRepository;
  
  public void checkDuplicate(String username, String ssn, String phone, String email) {
    if(userRepository.existsByEmail(email)){
      throw new ConfictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_EMAIL,email));
    }
    if(userRepository.existsByPhoneNumber(phone)){
      throw new ConfictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER,phone));
    }
    if(userRepository.existsBySsn(ssn)){
      throw new ConfictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_SSN,ssn));
    }
    if(userRepository.existsByUsername(username)){
      throw new ConfictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_USERNAME,username));
    }
  }
  

}
