package com.project.schoolmanagment.service.businnes;

import com.project.schoolmanagment.entity.concretes.business.EducationTerm;
import com.project.schoolmanagment.entity.concretes.business.Lesson;
import com.project.schoolmanagment.payload.request.businnes.LessonProgramRequest;
import com.project.schoolmanagment.payload.response.businnes.LessonProgramResponse;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.repository.businnes.LessonProgramRepository;
import java.util.Set;
import javax.persistence.SecondaryTable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonProgramService {
  
  private final LessonProgramRepository lessonProgramRepository;
  private final LessonService lessonService;
  private final EducationTermService educationTermService;
  private final 

  public ResponseMessage<LessonProgramResponse> saveLessonProgram(LessonProgramRequest lessonProgramRequest) {
    //get lessons from lesson service
    Set<Lesson>lessons = lessonService.getLessonByIdSet(lessonProgramRequest.getLessonIdList());
    //get education term
    EducationTerm educationTerm = educationTermService.isEducationTermExist(
        lessonProgramRequest.getEducationTermId());
    //need to validate times
    
  }
}
