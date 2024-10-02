package com.project.schoolmanagment.controller.businnes;

import com.project.schoolmanagment.entity.concretes.business.Lesson;
import com.project.schoolmanagment.service.businnes.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Set;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {

  private final LessonService lessonService;

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @GetMapping("/getLessonByIdSet")
  public Set<Lesson> getAllLessonByIdSet(
          @RequestParam(name = "lessonId") Set<Long> idSet) {
    return lessonService.getLessonByIdSet(idSet);
  }
}
