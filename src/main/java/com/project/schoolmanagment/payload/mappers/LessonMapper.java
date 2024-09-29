package com.project.schoolmanagment.payload.mappers;

import com.project.schoolmanagment.entity.concretes.business.Lesson;
import com.project.schoolmanagment.payload.request.businnes.LessonRequest;
import com.project.schoolmanagment.payload.response.businnes.LessonResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LessonMapper {

  public Lesson mapLessonRequestToLesson(LessonRequest lessonRequest){
    return Lesson.builder()
        .lessonName(lessonRequest.getLessonName())
        .creditScore(lessonRequest.getCreditScore())
        .isCompulsory(lessonRequest.getIsCompulsory())
        .build();
  }
  
  public LessonResponse mapLessonToLessonResponse(Lesson lesson){
    return LessonResponse.builder()
        .lessonId(lesson.getId())
        .lessonName(lesson.getLessonName())
        .creditScore(lesson.getCreditScore())
        .isCompulsory(lesson.getIsCompulsory())
        .build();
  }
  
  
  
}
