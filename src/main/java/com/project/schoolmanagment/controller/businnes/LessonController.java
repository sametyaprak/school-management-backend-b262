package com.project.schoolmanagment.controller.businnes;

import com.project.schoolmanagment.payload.request.businnes.LessonRequest;
import com.project.schoolmanagment.payload.response.businnes.LessonResponse;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.service.businnes.LessonService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {
  
  private final LessonService lessonService;
  
  @PostMapping("/save")
  public ResponseMessage<LessonResponse>saveLesson(
      @RequestBody @Valid LessonRequest lessonRequest) {
    return lessonService.saveLesson(lessonRequest);
  }

}
