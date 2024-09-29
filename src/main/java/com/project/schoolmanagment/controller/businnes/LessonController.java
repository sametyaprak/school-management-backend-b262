package com.project.schoolmanagment.controller.businnes;

import com.project.schoolmanagment.entity.concretes.business.Lesson;
import com.project.schoolmanagment.payload.mappers.LessonMapper;
import com.project.schoolmanagment.payload.request.businnes.LessonRequest;
import com.project.schoolmanagment.payload.response.businnes.LessonResponse;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.service.businnes.LessonService;
import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonController {
  
  private final LessonService lessonService;

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @PostMapping("/save")
  public ResponseMessage<LessonResponse>saveLesson(
      @RequestBody @Valid LessonRequest lessonRequest) {
    return lessonService.saveLesson(lessonRequest);
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @PutMapping("/update/{lessonId}")
  public ResponseEntity<LessonResponse>updateLessonById(
      @PathVariable Long lessonId,
      @RequestBody @Valid LessonRequest lessonRequest){
    return ResponseEntity.ok(lessonService.updateLesson(lessonId,lessonRequest));
  }

  //TODO
  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @DeleteMapping("/delete/{lessonId}")
  public ResponseMessage deleteLesson(@PathVariable Long lessonId) {
    //return lessonService.deleteLesson(lessonId);
    return null;
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @GetMapping("/getLessonByName")
  public ResponseMessage<LessonResponse>getLessonByName(
      @RequestParam String lessonName){
    return lessonService.findLessonByName(lessonName);
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @GetMapping("/getLessonByPage")
  public Page<LessonResponse>findLessonByPage(
      @RequestParam(value = "page",defaultValue = "0") int page,
      @RequestParam(value = "size",defaultValue = "10") int size,
      @RequestParam(value = "sort",defaultValue = "lessonName") String sort,
      @RequestParam(value = "type",defaultValue = "desc") String type
  ){
    return lessonService.getLessonByPage(page,size,sort,type);
  }


  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @GetMapping("/getLessonByIdSet")
  public Set<Lesson>getAllLessonByIdSet(
      @RequestParam(name = "lessonId") Set<Long>idSet) {
    return lessonService.getLessonByIdSet(idSet);
  }
  
  
  

}
