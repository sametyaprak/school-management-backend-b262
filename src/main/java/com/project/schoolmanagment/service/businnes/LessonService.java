package com.project.schoolmanagment.service.businnes;

import com.project.schoolmanagment.payload.request.businnes.LessonRequest;
import com.project.schoolmanagment.payload.response.businnes.LessonResponse;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.repository.businnes.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonService {
  
  private final LessonRepository lessonRepository;

  public ResponseMessage<LessonResponse> saveLesson(LessonRequest lessonRequest) {
    //validate - lesson name must be unique
    
    
  }
  
  private void  isLessonExistByLessonName(String lessonName){
    if(le)
  }
}
