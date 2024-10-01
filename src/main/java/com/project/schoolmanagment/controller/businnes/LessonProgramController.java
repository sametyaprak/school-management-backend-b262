package com.project.schoolmanagment.controller.businnes;

import com.project.schoolmanagment.entity.concretes.business.LessonProgram;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.service.businnes.LessonProgramService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lessonPrograms")
@RequiredArgsConstructor
public class LessonProgramController {


  private final LessonProgramService lessonProgramService;

  //TODO
  //RUMEYSA get by Id
  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Student','Teacher')")
  @GetMapping("/getLessonById/{id}")
  public ResponseMessage<LessonProgram> getLessonById(@PathVariable Long id) {
    return lessonProgramService.getLessonById(id);
  }
}
