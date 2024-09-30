package com.project.schoolmanagment.controller.businnes;

import com.project.schoolmanagment.payload.request.businnes.EducationTermRequest;
import com.project.schoolmanagment.payload.response.businnes.EducationTermResponse;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.service.businnes.EducationTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
            @RequestParam (value = "sort", defaultValue = "name") String sort,
            @RequestParam (value = "type", defaultValue = "desc") String type){
        //return educationTermService.getAllByPage(page,size,sort,type);
        return null;
    }

    //TODO RUMEYSA
    @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher')")
    @DeleteMapping("/delete/{id}")
    public ResponseMessage deleteEducationTerm(@PathVariable Long id){
        return educationTermService.deleteById(id); // return null
       }


}
