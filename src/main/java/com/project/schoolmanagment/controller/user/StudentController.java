package com.project.schoolmanagment.controller.user;

import com.project.schoolmanagment.payload.request.businnes.AddLessonProgram;
import com.project.schoolmanagment.payload.request.businnes.AddLessonProgramForStudent;
import com.project.schoolmanagment.payload.request.user.StudentRequest;
import com.project.schoolmanagment.payload.request.user.StudentUpdateRequest;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.payload.response.user.StudentResponse;
import com.project.schoolmanagment.service.user.StudentService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
  
  private final StudentService studentService;

  @PreAuthorize("hasAnyAuthority('Admin')")
  @PostMapping("/save")
  public ResponseMessage<StudentResponse>save(@RequestBody @Valid StudentRequest studentRequest) {
    return studentService.saveStudent(studentRequest);
  }

  @PreAuthorize("hasAnyAuthority('Student')")
  @PatchMapping("/update")
  public ResponseEntity<String>updateStudent(
      @RequestBody @Valid StudentUpdateRequest studentUpdateRequest,
      HttpServletRequest httpServletRequest){
    return studentService.updateStudent(studentUpdateRequest,httpServletRequest);
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @PatchMapping("/update/{userId}")
  public ResponseMessage<StudentResponse>updateStudentByManager(
      @PathVariable Long userId,
      @RequestBody @Valid StudentRequest studentRequest){
    return studentService.updateStudentByManager(userId,studentRequest);
  }

  @PreAuthorize("hasAnyAuthority('Student')")
  @PostMapping("/addLessonProgramToStudent")
  public ResponseMessage<StudentResponse> addLessonProgram(
      HttpServletRequest servletRequest,
      @RequestBody @Valid AddLessonProgramForStudent addLessonProgramForStudent){
    return studentService.addLessonProgram(servletRequest,addLessonProgramForStudent);
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @GetMapping("/changeStatus")
  public ResponseMessage changeStatus(@RequestParam Long id,
      @RequestParam boolean status){
    return studentService.changeStatus(id,status);
  }
  
  

}
