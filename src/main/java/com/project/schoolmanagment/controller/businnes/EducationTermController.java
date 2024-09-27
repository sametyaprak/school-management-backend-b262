package com.project.schoolmanagment.controller.businnes;

import com.project.schoolmanagment.payload.request.businnes.EducationTermRequest;
import com.project.schoolmanagment.payload.response.businnes.EducationTermResponse;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.service.businnes.EducationTermService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/educationTerm")
@RequiredArgsConstructor
public class EducationTermController {
  
  private final EducationTermService educationTermService;

  @PreAuthorize("hasAnyAuthority('Admin','Dean')")
  @PostMapping("/save")
  public ResponseMessage<EducationTermResponse>saveEducationTerm(
      @Valid @RequestBody EducationTermRequest educationTermRequest) {
    return educationTermService.saveEducationTerm(educationTermRequest);
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
  @PutMapping("/update/{id}")
  public ResponseMessage<EducationTermResponse>updateEducationTerm(
      @PathVariable Long id,
      @RequestBody EducationTermRequest educationTermRequest){
    return educationTermService.updateEducationTerm(educationTermRequest,id);
  }

}
