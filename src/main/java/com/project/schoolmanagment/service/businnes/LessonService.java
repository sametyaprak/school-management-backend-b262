package com.project.schoolmanagment.service.businnes;

import com.project.schoolmanagment.entity.concretes.business.Lesson;
import com.project.schoolmanagment.exception.ConfictException;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.mappers.LessonMapper;
import com.project.schoolmanagment.payload.messages.ErrorMessages;
import com.project.schoolmanagment.payload.messages.SuccessMessages;
import com.project.schoolmanagment.payload.request.businnes.LessonRequest;
import com.project.schoolmanagment.payload.response.businnes.LessonResponse;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.repository.businnes.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonService {
  
  private final LessonRepository lessonRepository;
  private final LessonMapper lessonMapper;

  public ResponseMessage<LessonResponse> saveLesson(LessonRequest lessonRequest) {
    //validate - lesson name must be unique
    isLessonExistByLessonName(lessonRequest.getLessonName());
    //map DTO->entity
    Lesson lesson = lessonMapper.mapLessonRequestToLesson(lessonRequest);
    Lesson savedLesson = lessonRepository.save(lesson);
    return ResponseMessage.<LessonResponse>builder()
        .returnBody(lessonMapper.mapLessonToLessonResponse(savedLesson))
        .httpStatus(HttpStatus.CREATED)
        .message(SuccessMessages.LESSON_SAVE)
        .build();    
  }
  
  private void  isLessonExistByLessonName(String lessonName){
    if(lessonRepository.getByLessonNameEqualsIgnoreCase(lessonName).isPresent()){
      throw new ConfictException(String.format(ErrorMessages.ALREADY_CREATED_LESSON_MESSAGE,lessonName));
    }
  }
  private Lesson isLessonExistById(Long id){
    return lessonRepository.findById(id)
        .orElseThrow(()-> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_LESSON_MESSAGE,id)));
  }


  public LessonResponse updateLesson(Long lessonId, LessonRequest lessonRequest) {
    //validate if lesson exists
    Lesson lesson = isLessonExistById(lessonId);
    //lesson name must be unique
    if(!lesson.getLessonName().equals(lessonRequest.getLessonName())){
      //user is changing lesson name
      isLessonExistByLessonName(lessonRequest.getLessonName());
    }
    Lesson updatedLesson = lessonMapper.mapLessonRequestToLesson(lessonRequest);
    updatedLesson.setId(lesson.getId());
    Lesson savedLesson = lessonRepository.save(updatedLesson);
    return lessonMapper.mapLessonToLessonResponse(savedLesson);
  }
}
