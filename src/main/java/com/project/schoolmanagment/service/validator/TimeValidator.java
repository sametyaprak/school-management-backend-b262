package com.project.schoolmanagment.service.validator;

import com.project.schoolmanagment.exception.BadRequestException;
import com.project.schoolmanagment.payload.messages.ErrorMessages;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.stereotype.Component;

@Component
public class TimeValidator {
  
  //we need to validate if start is before stop
  public boolean checkTime(LocalTime start, LocalTime stop){
    return start.isAfter(stop) || start.equals(stop);
  }
  
  public void checkTimeWithException(LocalTime start, LocalTime stop){
    if(checkTime(start, stop)){
      throw new BadRequestException(ErrorMessages.TIME_NOT_VALID_MESSAGE);
    }
  }

}
