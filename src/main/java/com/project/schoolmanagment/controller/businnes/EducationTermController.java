package com.project.schoolmanagment.controller.businnes;

import com.project.schoolmanagment.payload.request.businnes.EducationTermRequest;
import com.project.schoolmanagment.payload.response.businnes.EducationTermResponse;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.service.businnes.EducationTermService;
import com.sun.xml.bind.v2.TODO;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  
  //TODO HULYA
  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
  @GetMapping("/getAll")
  public List<EducationTermResponse> getAllEducationTerms(){
    //return educationTermService.getAllEducationTerms();
    return null;
  }
  

  //TODO HULYA
  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
  @GetMapping("/{id}")
  public EducationTermResponse getEducationTermById(@PathVariable Long id){
    //return educationTermService.findById(id);
    return null;
  }

  //TODO URANUS
  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
  @GetMapping("/getAllEducationTermsByPage")
  public Page<EducationTermResponse> getAllEducationTermsByPage(
      @RequestParam(value="page",defaultValue= "0") int page,
      @RequestParam (value = "size", defaultValue = "10") int size,
      @RequestParam (value = "sort", defaultValue = "term") String sort,
      @RequestParam (value = "type", defaultValue = "desc") String type){
    return educationTermService.getAllByPage(page,size,sort,type);
  }

  //TODO RUMEYSA
  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
  @DeleteMapping("/delete/{id}")
  public ResponseMessage deleteEducationTerm(@PathVariable Long id){
    //return educationTermService.deleteById(id);
    return null;
  }

}
