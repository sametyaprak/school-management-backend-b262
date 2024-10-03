package com.project.schoolmanagment.controller.user;

import com.project.schoolmanagment.payload.request.businnes.AddLessonProgram;
import com.project.schoolmanagment.payload.request.user.TeacherRequest;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.payload.response.user.StudentResponse;
import com.project.schoolmanagment.payload.response.user.UserResponse;
import com.project.schoolmanagment.service.user.TeacherService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

  private final TeacherService teacherService;

  @PreAuthorize("hasAnyAuthority('Admin')")
  @PostMapping("/save")
  public ResponseMessage<UserResponse> saveTeacher(
      @RequestBody @Valid TeacherRequest teacherRequest) {
    return teacherService.save(teacherRequest);
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @PutMapping("/update/{userId}")
  public ResponseMessage<UserResponse> updateTeacher(
      @RequestBody @Valid TeacherRequest teacherRequest,
      @PathVariable Long userId) {
    return teacherService.updateTeacher(teacherRequest, userId);
  }

  @PreAuthorize("hasAnyAuthority('Teacher')")
  @GetMapping("/getAllStudentByAdvisorTeacher")
  public List<StudentResponse>getAllStudentByAdvisorTeacher(
      HttpServletRequest httpServletRequest){
    return teacherService.getAllStudentByAdvisorTeacher(httpServletRequest);
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @PostMapping("/addLessonProgram")
  public ResponseMessage<UserResponse> addLessonProgram(
      @RequestBody @Valid AddLessonProgram lessonProgram) {
    return teacherService.addLessonProgram(lessonProgram);
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @DeleteMapping("/deleteTeacherById/{id}")
  public ResponseMessage<UserResponse> deleteById(@PathVariable Long id) {
    return teacherService.deleteById(id);
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @GetMapping("/getAllTeacher")
  public List<UserResponse> getAllTeacher() {
    return teacherService.getAllTeacher();
  }


  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @GetMapping("/getTeachersByPage")
  public Page<UserResponse> getTeachersByPage(
      @RequestParam(value = "page", defaultValue = "0") int page,
      @RequestParam(value = "size", defaultValue = "5") int size,
      @RequestParam(value = "sort", defaultValue = "username") String sort,
      @RequestParam(value = "type", defaultValue = "desc") String type) {
    return teacherService.getTeachersByPage(page, size, sort, type);


  }

}
