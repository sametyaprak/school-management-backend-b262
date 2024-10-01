package com.project.schoolmanagment.controller.businnes;


import com.project.schoolmanagment.payload.request.businnes.LessonProgramRequest;
import com.project.schoolmanagment.payload.response.businnes.LessonProgramResponse;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.service.businnes.LessonProgramService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lessonPrograms")
@RequiredArgsConstructor
public class LessonProgramController {
  
  private final LessonProgramService lessonProgramService;


  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @PostMapping("/save")
  public ResponseMessage<LessonProgramResponse>saveLessonProgram(
      @RequestBody @Valid LessonProgramRequest lessonProgramRequest) {
    return lessonProgramService.saveLessonProgram(lessonProgramRequest);
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher','Student')")
  @GetMapping("/getAll")
  public List<LessonProgramResponse> getAllLessonPrograms(){
    return lessonProgramService.getAllLessonPrograms();
  }
  
  //TODO
  //RUMEYSA get by Id
  
  //TODO
  //URANUS delete by Id

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Student','Teacher')")
  @GetMapping("/getAllUnassigned")
  public List<LessonProgramResponse>getAllUnassigned(){
    return lessonProgramService.getAllUnassigned();    
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Student','Teacher')")
  @GetMapping("/getAllAssigned")
  public List<LessonProgramResponse>getAllAssigned(){
    return lessonProgramService.getAllAssigned();
  }
  
  
  

}
