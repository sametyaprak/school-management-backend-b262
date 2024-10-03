package com.project.schoolmanagment.service.businnes;

import com.project.schoolmanagment.entity.concretes.business.EducationTerm;
import com.project.schoolmanagment.entity.concretes.business.Lesson;
import com.project.schoolmanagment.entity.concretes.business.LessonProgram;
import com.project.schoolmanagment.exception.BadRequestException;
import com.project.schoolmanagment.payload.mappers.LessonProgramMapper;
import com.project.schoolmanagment.payload.messages.ErrorMessages;
import com.project.schoolmanagment.payload.messages.SuccessMessages;
import com.project.schoolmanagment.payload.request.businnes.LessonProgramRequest;
import com.project.schoolmanagment.payload.response.businnes.LessonProgramResponse;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.repository.businnes.LessonProgramRepository;
import com.project.schoolmanagment.service.validator.TimeValidator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.SecondaryTable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonProgramService {
  
  private final LessonProgramRepository lessonProgramRepository;
  private final LessonService lessonService;
  private final EducationTermService educationTermService;
  private final TimeValidator timeValidator;
  private final LessonProgramMapper lessonProgramMapper;

  public ResponseMessage<LessonProgramResponse> saveLessonProgram(LessonProgramRequest lessonProgramRequest) {
    //get lessons from lesson service
    Set<Lesson>lessons = lessonService.getLessonByIdSet(lessonProgramRequest.getLessonIdList());
    //get education term
    EducationTerm educationTerm = educationTermService.isEducationTermExist(
        lessonProgramRequest.getEducationTermId());
    //need to validate times
    timeValidator.checkTimeWithException(
        lessonProgramRequest.getStartTime(),
        lessonProgramRequest.getStopTime());
    //map DTO to entity
    LessonProgram lessonProgram = lessonProgramMapper.mapLessonProgramRequestToLessonProgram(
        lessonProgramRequest,
        lessons,
        educationTerm);
    LessonProgram savedLessonProgram = lessonProgramRepository.save(lessonProgram);
    return ResponseMessage.<LessonProgramResponse>
        builder()
        .returnBody(lessonProgramMapper.mapLessonProgramToLessonProgramResponse(savedLessonProgram))
        .httpStatus(HttpStatus.CREATED)
        .message(SuccessMessages.LESSON_PROGRAM_SAVE)
        .build();    
  }

  public List<LessonProgramResponse> getAllUnassigned() {
    return lessonProgramRepository.findByUsers_IdNull()
        .stream()
        .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
        .collect(Collectors.toList());  
  }

  public List<LessonProgramResponse> getAllAssigned() {
    return lessonProgramRepository.findByUsers_IdNotNull()
        .stream()
        .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
        .collect(Collectors.toList());
  }

  public List<LessonProgramResponse> getAllLessonPrograms() {
    return lessonProgramRepository.findAll().stream()
            .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
            .collect(Collectors.toList());
  }
  
  public Set<LessonProgram>getLessonProgramById(Set<Long>lessonIdSet){
    Set<LessonProgram>lessonProgramSet = lessonProgramRepository.getLessonProgramByIdList(lessonIdSet);
    if(lessonProgramSet.isEmpty()){
      throw new BadRequestException(ErrorMessages.NOT_FOUND_LESSON_PROGRAM_MESSAGE_WITHOUT_ID_INFO);
    }
    return lessonProgramSet;
  }
}
