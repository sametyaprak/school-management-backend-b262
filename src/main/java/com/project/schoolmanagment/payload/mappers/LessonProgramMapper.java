package com.project.schoolmanagment.payload.mappers;

import com.project.schoolmanagment.entity.concretes.business.EducationTerm;
import com.project.schoolmanagment.entity.concretes.business.Lesson;
import com.project.schoolmanagment.entity.concretes.business.LessonProgram;
import com.project.schoolmanagment.payload.request.businnes.LessonProgramRequest;
import com.project.schoolmanagment.payload.response.businnes.LessonProgramResponse;
import java.util.Set;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LessonProgramMapper {

  public LessonProgram mapLessonProgramRequestToLessonProgram(
      LessonProgramRequest lessonProgramRequest,
      Set<Lesson>lessonSet,
      EducationTerm educationTerm) {
    return LessonProgram.builder()
        .startTime(lessonProgramRequest.getStartTime())
        .stopTime(lessonProgramRequest.getStopTime())
        .day(lessonProgramRequest.getDay())
        .lessons(lessonSet)
        .educationTerm(educationTerm)
        .build();
  }
  
  public LessonProgramResponse mapLessonProgramToLessonProgramResponse(
      LessonProgram lessonProgram){
    return LessonProgramResponse.builder()
        .day(lessonProgram.getDay())
        .educationTerm(lessonProgram.getEducationTerm())
        .startTime(lessonProgram.getStartTime())
        .stopTime(lessonProgram.getStopTime())
        .lessonProgramId(lessonProgram.getId())
        .build();
  }
  
}
