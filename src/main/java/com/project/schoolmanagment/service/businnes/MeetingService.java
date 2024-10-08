package com.project.schoolmanagment.service.businnes;

import com.project.schoolmanagment.entity.concretes.business.Meeting;
import com.project.schoolmanagment.entity.concretes.user.User;
import com.project.schoolmanagment.payload.mappers.MeetingMapper;
import com.project.schoolmanagment.payload.messages.SuccessMessages;
import com.project.schoolmanagment.payload.request.businnes.MeetingRequest;
import com.project.schoolmanagment.payload.response.businnes.MeetingResponse;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.repository.businnes.MeetingRepository;
import com.project.schoolmanagment.service.helper.MeetingHelper;
import com.project.schoolmanagment.service.helper.MethodHelper;
import com.project.schoolmanagment.service.helper.PageableHelper;
import com.project.schoolmanagment.service.validator.TimeValidator;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingService {
  
  private final MethodHelper methodHelper;
  private final TimeValidator timeValidator;
  private final MeetingHelper meetingHelper;
  private final MeetingMapper meetingMapper;
  private final MeetingRepository meetingRepository;
  private final PageableHelper pageableHelper;

  public ResponseMessage<MeetingResponse> saveMeeting(HttpServletRequest httpServletRequest,
      MeetingRequest meetingRequest) {
    String username = (String) httpServletRequest.getAttribute("username");
    User teacher = methodHelper.loadByUsername(username);
    methodHelper.checkIsAdvisor(teacher);
    timeValidator.checkTimeWithException(
        meetingRequest.getStartTime(),
        meetingRequest.getStopTime());
    //validate meetings conflict
    meetingHelper.checkMeetingConflicts(
        meetingRequest.getStudentIds(),
        teacher.getId(), 
        meetingRequest.getDate(),
        meetingRequest.getStartTime(),
        meetingRequest.getStopTime());

    List<User>students = methodHelper.getUserList(meetingRequest.getStudentIds());
    Meeting meeting = meetingMapper.mapMeetingRequestToMeet(meetingRequest);
    meeting.setStudentList(students);
    meeting.setAdvisoryTeacher(teacher);
    Meeting savedMeeting = meetingRepository.save(meeting);
    return ResponseMessage.<MeetingResponse>builder()
        .message(SuccessMessages.MEET_SAVE)
        .returnBody(meetingMapper.mapMeetingToMeetingResponse(savedMeeting))
        .httpStatus(HttpStatus.CREATED)
        .build();   
  }

  public ResponseMessage<MeetingResponse> update(MeetingRequest meetingRequest, Long meetingId,
      HttpServletRequest servletRequest) {
    //validate existence
    Meeting meeting = meetingHelper.isMeetingExistById(meetingId);
    //we have to check if teacher and meeting are matching
    meetingHelper.isMeetingMatchedWithTeacher(meeting,servletRequest);
    timeValidator.checkTimeWithException(meetingRequest.getStartTime(),meetingRequest.getStopTime());
    meetingHelper.checkMeetingConflicts(
        meetingRequest.getStudentIds(),
        meeting.getAdvisoryTeacher().getId(),
        meetingRequest.getDate(),
        meetingRequest.getStartTime(),
        meetingRequest.getStopTime());
    List<User>students = methodHelper.getUserList(meetingRequest.getStudentIds());
    Meeting meetToUpdate = meetingMapper.mapMeetingRequestToMeet(meetingRequest);
    meetToUpdate.setStudentList(students);
    meetToUpdate.setAdvisoryTeacher(meeting.getAdvisoryTeacher());
    meetToUpdate.setId(meetingId);
    Meeting savedMeeting = meetingRepository.save(meetToUpdate);
    return ResponseMessage.<MeetingResponse>
        builder()
        .message(SuccessMessages.MEET_UPDATE)
        .returnBody(meetingMapper.mapMeetingToMeetingResponse(savedMeeting))
        .httpStatus(HttpStatus.OK)
        .build();
  }

  public ResponseMessage<Object> deleteById(Long meetingId) {
    meetingHelper.isMeetingExistById(meetingId);
    meetingRepository.deleteById(meetingId);
    return ResponseMessage.builder()
        .message(SuccessMessages.MEET_DELETE)
        .httpStatus(HttpStatus.OK)
        .build();    
  }

  public Page<MeetingResponse> getAllByPageTeacher(int page, int size,
      HttpServletRequest httpServletRequest) {
    String username = (String) httpServletRequest.getAttribute("username");
    User teacher = methodHelper.loadByUsername(username);
    methodHelper.checkIsAdvisor(teacher);
    Pageable pageable = pageableHelper.getPageable(page, size);
    return meetingRepository.getByAdvisoryTeacher_IdEquals(teacher.getId(),pageable)
        .map(meetingMapper::mapMeetingToMeetingResponse);
  }

    public List<MeetingResponse> getAll(HttpServletRequest httpServletRequest) {
      return meetingRepository.findAll().stream()
              .map(meetingMapper::mapMeetingToMeetingResponse)
              .collect(Collectors.toList());
    }

  public Page<MeetingResponse> getAllByPage(int page, int size) {
    Pageable pageable = pageableHelper.getPageable(page,size);
    return meetingRepository.findAll(pageable)
            .map(meetingMapper::mapMeetingToMeetingResponse);
  }
}
