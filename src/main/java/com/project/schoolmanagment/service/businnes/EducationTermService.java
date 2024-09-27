package com.project.schoolmanagment.service.businnes;

import com.project.schoolmanagment.exception.ConfictException;
import com.project.schoolmanagment.payload.request.businnes.EducationTermRequest;
import com.project.schoolmanagment.payload.response.businnes.EducationTermResponse;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import org.springframework.stereotype.Service;

@Service
public class EducationTermService {

  public ResponseMessage<EducationTermResponse> saveEducationTerm(
      EducationTermRequest educationTermRequest) {
    //validation
    
  }
  
  
  private void validateEducationTermDatesForRequest(EducationTermRequest educationTermRequest) {
    //reg<start
    if(educationTermRequest.getLastRegistrationDate().isAfter(educationTermRequest.getStartDate())){
      throw new ConfictException()
    }
  }
  
  
}
