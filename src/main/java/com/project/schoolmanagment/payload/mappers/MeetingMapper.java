package com.project.schoolmanagment.payload.mappers;

import com.project.schoolmanagment.entity.concretes.business.Meeting;
import com.project.schoolmanagment.payload.request.businnes.MeetingRequest;
import com.project.schoolmanagment.payload.response.businnes.MeetingResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class MeetingMapper {
  
  
  public Meeting mapMeetingRequestToMeet(MeetingRequest meetingRequest){
    return Meeting.builder()
        .date(meetingRequest.getDate())
        .startTime(meetingRequest.getStartTime())
        .stopTime(meetingRequest.getStopTime())
        .description(meetingRequest.getDescription())
        .build();
  }
  
  public MeetingResponse mapMeetingToMeetingResponse(Meeting meeting){
    return MeetingResponse.builder()
        .id(meeting.getId())
        .date(meeting.getDate())
        .startTime(meeting.getStartTime())
        .stopTime(meeting.getStopTime())
        .description(meeting.getDescription())
        .advisorTeacherId(meeting.getAdvisoryTeacher().getId())
        .teacherSsn(meeting.getAdvisoryTeacher().getSsn())
        .teacherName(meeting.getAdvisoryTeacher().getName())
        .students(meeting.getStudentList())
        .build();
  }
  

}
