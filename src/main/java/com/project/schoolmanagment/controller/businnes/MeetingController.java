package com.project.schoolmanagment.controller.businnes;

import com.project.schoolmanagment.payload.request.businnes.MeetingRequest;
import com.project.schoolmanagment.payload.response.businnes.MeetingResponse;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.service.businnes.MeetingService;
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

@RequiredArgsConstructor
@RequestMapping("/meet")
@RestController
public class MeetingController {
  
  private final MeetingService meetingService;
  
  
  @PreAuthorize("hasAnyAuthority('Teacher')")
  @PostMapping("/save")
  public ResponseMessage<MeetingResponse>saveMeeting(
      HttpServletRequest httpServletRequest,
      @RequestBody @Valid MeetingRequest meetingRequest){
    return meetingService.saveMeeting(httpServletRequest,meetingRequest);
  }

  @PreAuthorize("hasAnyAuthority('Teacher')")
  @PutMapping("/update/{meetingId}")
  public ResponseMessage<MeetingResponse>updateMeeting(
      @RequestBody @Valid MeetingRequest meetingRequest,
      @PathVariable Long meetingId,
      HttpServletRequest servletRequest){
    return meetingService.update(meetingRequest,meetingId,servletRequest);
  }

  @PreAuthorize("hasAnyAuthority('Admin','Teacher')")
  @DeleteMapping("/delete/{meetingId}")
  public ResponseMessage deleteById(@PathVariable Long meetingId){
    return meetingService.deleteById(meetingId);
  }

  @PreAuthorize("hasAnyAuthority('Teacher')")
  @GetMapping("/getAllByPageTeacher")
  public Page<MeetingResponse>getAllByPageTeacher(
      HttpServletRequest httpServletRequest,
      @RequestParam(value = "page",defaultValue = "0") int page,
      @RequestParam(value = "size",defaultValue = "10") int size){
    return meetingService.getAllByPageTeacher(page,size,httpServletRequest);
  }


  //TODO HULYA
  @PreAuthorize("hasAnyAuthority('Teacher','Student')")
  @GetMapping("/getAll")
  public List<MeetingResponse> getAllMeetings(HttpServletRequest httpServletRequest){
    //return meetingService.getAll(httpServletRequest);
    return null;
  }

  //TODO HULYA
  @PreAuthorize("hasAnyAuthority('Admin')")
  @GetMapping("/getAllByPage")
  public Page<MeetingResponse>getAllByPage(
      @RequestParam(value = "page") int page,
      @RequestParam(value = "size") int size){
    //return meetingService.getAllByPage(page, size);
    return null;
  }
  

}
