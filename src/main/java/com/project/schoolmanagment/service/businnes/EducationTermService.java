package com.project.schoolmanagment.service.businnes;

import com.project.schoolmanagment.entity.concretes.business.EducationTerm;
import com.project.schoolmanagment.exception.BadRequestException;
import com.project.schoolmanagment.exception.ConfictException;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.mappers.EducationTermMapper;
import com.project.schoolmanagment.payload.messages.ErrorMessages;
import com.project.schoolmanagment.payload.messages.SuccessMessages;
import com.project.schoolmanagment.payload.request.businnes.EducationTermRequest;
import com.project.schoolmanagment.payload.response.businnes.EducationTermResponse;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.repository.businnes.EducationTermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducationTermService {
  
  private final EducationTermRepository educationTermRepository;
  private final EducationTermMapper educationTermMapper;

  public ResponseMessage<EducationTermResponse> saveEducationTerm(
      EducationTermRequest educationTermRequest) {
    //validation
    validateEducationTermDates(educationTermRequest);
    //mapping
    EducationTerm educationTerm = educationTermMapper.mapEducationTermRequestToEducationTerm(educationTermRequest);
    EducationTerm savedEducationTerm = educationTermRepository.save(educationTerm);
    
    return ResponseMessage.<EducationTermResponse>builder()
        .message(SuccessMessages.EDUCATION_TERM_SAVE)
        .returnBody(educationTermMapper.mapEducationTermToEducationTermResponse(savedEducationTerm))
        .httpStatus(HttpStatus.CREATED)
        .build();
  }
  
  private void validateEducationTermDates(EducationTermRequest educationTermRequest) {
    validateEducationTermDatesForRequest(educationTermRequest);
    //only one education term can exist in a year
    if(educationTermRepository.existsByTermAndYear(
        educationTermRequest.getTerm(),educationTermRequest.getStartDate().getYear())){
      throw new ConfictException(ErrorMessages.EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR_MESSAGE);
    }
    //validate not to have any conflict with other education terms
    educationTermRepository.findByYear(educationTermRequest.getStartDate().getYear())
        .forEach(educationTerm -> {
          if (!educationTerm.getStartDate().isAfter(educationTermRequest.getEndDate())
              || educationTerm.getEndDate().isBefore(educationTermRequest.getStartDate())) {
            throw new BadRequestException(ErrorMessages.EDUCATION_TERM_CONFLICT_MESSAGE);
          }          
        });        
  }
        
  
  
  private void validateEducationTermDatesForRequest(EducationTermRequest educationTermRequest) {
    //reg<start
    if(educationTermRequest.getLastRegistrationDate().isAfter(educationTermRequest.getStartDate())){
      throw new ConfictException(ErrorMessages.EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE);
    }
    //end>start
    if(educationTermRequest.getEndDate().isBefore(educationTermRequest.getStartDate())){
      throw new ConfictException(ErrorMessages.EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE);
    }
  }


  public ResponseMessage<EducationTermResponse> updateEducationTerm(
      EducationTermRequest educationTermRequest, Long id) {
    //check if education term exists
    isEducationTermExist(id);
    //validate dates
    validateEducationTermDatesForRequest(educationTermRequest);
    //map
    EducationTerm term = educationTermMapper.mapEducationTermRequestToEducationTerm(educationTermRequest);
    term.setId(term.getId());
    //save to DB
    EducationTerm savedEducationTerm = educationTermRepository.save(term);
    //return by mapping it to DTO
    return ResponseMessage.<EducationTermResponse>builder()
        .message(SuccessMessages.EDUCATION_TERM_UPDATE)
        .returnBody(educationTermMapper.mapEducationTermToEducationTermResponse(savedEducationTerm))
        .httpStatus(HttpStatus.OK)
        .build();
  }
  
  
  public EducationTerm isEducationTermExist(Long id){
    return educationTermRepository.findById(id)
        .orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessages.EDUCATION_TERM_NOT_FOUND_MESSAGE,id)));
  }
}
