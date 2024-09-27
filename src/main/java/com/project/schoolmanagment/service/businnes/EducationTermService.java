package com.project.schoolmanagment.service.businnes;

import com.project.schoolmanagment.entity.concretes.business.EducationTerm;
import com.project.schoolmanagment.exception.BadRequestException;
import com.project.schoolmanagment.exception.ConfictException;
import com.project.schoolmanagment.payload.messages.ErrorMessages;
import com.project.schoolmanagment.payload.request.businnes.EducationTermRequest;
import com.project.schoolmanagment.payload.response.businnes.EducationTermResponse;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.repository.businnes.EducationTermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducationTermService {
  
  private final EducationTermRepository educationTermRepository;

  public ResponseMessage<EducationTermResponse> saveEducationTerm(
      EducationTermRequest educationTermRequest) {
    //validation
    
  }
  
  private void validateEducationTermDates(EducationTermRequest educationTermRequest) {
    validateEducationTermDatesForRequest(educationTermRequest);
    //only one education term can exist in a year
    if(educationTermRepository.existsByTermAndYear(
        educationTermRequest.getTerm(),educationTermRequest.getStartDate().getYear())){
      throw new ConfictException(ErrorMessages.EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR_MESSAGE);
    }
    //validate not to have any conflict with other education terms
    if(educationTermRepository.findByYear(educationTermRequest.getStartDate().getYear())
        .stream()
        .anyMatch(educationTerm ->
            (educationTerm.getStartDate().equals(educationTermRequest.getStartDate())
                || (educationTerm.getStartDate().isBefore(educationTermRequest.getStartDate())
                && educationTerm.getEndDate().isAfter(educationTermRequest.getStartDate()))
                || (educationTerm.getStartDate().isBefore(educationTermRequest.getEndDate())
                && educationTerm.getEndDate().isAfter(educationTermRequest.getEndDate()))
                || (educationTerm.getStartDate().isAfter(educationTermRequest.getStartDate())
                && educationTerm.getEndDate().isBefore(educationTermRequest.getEndDate()))))) {
      throw new BadRequestException(ErrorMessages.EDUCATION_TERM_CONFLICT_MESSAGE);
    }
    
  }
  
  
  private void validateEducationTermDatesForRequest(EducationTermRequest educationTermRequest) {
    //reg<start
    if(educationTermRequest.getLastRegistrationDate().isAfter(educationTermRequest.getStartDate())){
      throw new ConfictException(ErrorMessages.EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE);
    }
    //end>start
    if(educationTermRequest.getEndDate().isAfter(educationTermRequest.getStartDate())){
      throw new ConfictException(ErrorMessages.EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE);
    }
  }
  
  
}
