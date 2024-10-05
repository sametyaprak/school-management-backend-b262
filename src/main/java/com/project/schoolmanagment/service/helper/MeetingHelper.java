package com.project.schoolmanagment.service.helper;

import com.project.schoolmanagment.entity.concretes.business.Meeting;
import com.project.schoolmanagment.entity.concretes.user.User;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exception.BadRequestException;
import com.project.schoolmanagment.exception.ConfictException;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.messages.ErrorMessages;
import com.project.schoolmanagment.repository.businnes.MeetingRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MeetingHelper {
  
  private final MethodHelper methodHelper;
  private final MeetingRepository meetingRepository;
  
  public void checkMeetingConflicts(
      List<Long>studentIdList, Long teacherId, LocalDate meetingDate,
      LocalTime startTime, LocalTime stopTime){
    Set<Meeting> existingMeetings = new HashSet<>();
    for (Long id:studentIdList){
      //check a student really exist + is a student
      methodHelper.checkRole(methodHelper.isUserExist(id), RoleType.STUDENT);
      existingMeetings.addAll(meetingRepository.findByStudentList_IdEquals(id));
    }
    existingMeetings.addAll(meetingRepository.getByAdvisoryTeacher_IdEquals(teacherId));
    for (Meeting meet:existingMeetings){
      if (existingMeetings.size()==1){
        continue;
      } else {
        LocalTime existingStartTime = meet.getStartTime();
        LocalTime existingStopTime = meet.getStopTime();
        if(meet.getDate().equals(meetingDate) && (
            (startTime.isAfter(existingStartTime) && startTime.isBefore(existingStopTime)) ||
                (stopTime.isAfter(existingStartTime) && stopTime.isBefore(existingStopTime)) ||
                (startTime.isBefore(existingStartTime) && stopTime.isAfter(existingStopTime)) ||
                (startTime.equals(existingStartTime) || stopTime.equals(existingStopTime))
        )) {
          throw new ConfictException(ErrorMessages.MEET_HOURS_CONFLICT);
        }
      }

    }
  }
  
  public Meeting isMeetingExistById(Long id){
    return meetingRepository.findById(id)
        .orElseThrow(
            ()->new ResourceNotFoundException(String.format(ErrorMessages.MEET_NOT_FOUND_MESSAGE,id)));
  }
  
  public void isMeetingMatchedWithTeacher(Meeting meeting, HttpServletRequest servletRequest){
    String username = (String) servletRequest.getAttribute("username");
    User teacher = methodHelper.loadByUsername(username);
    methodHelper.checkIsAdvisor(teacher);
    if(!meeting.getAdvisoryTeacher().getId().equals(teacher.getId())){
      throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
    }
  }

}
