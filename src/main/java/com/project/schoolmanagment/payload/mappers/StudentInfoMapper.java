package com.project.schoolmanagment.payload.mappers;

import com.project.schoolmanagment.entity.concretes.business.EducationTerm;
import com.project.schoolmanagment.entity.concretes.business.Lesson;
import com.project.schoolmanagment.entity.concretes.business.StudentInfo;
import com.project.schoolmanagment.entity.enums.Note;
import com.project.schoolmanagment.payload.request.businnes.StudentInfoRequest;
import com.project.schoolmanagment.payload.request.businnes.StudentInfoUpdateRequest;
import com.project.schoolmanagment.payload.response.businnes.StudentInfoResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@RequiredArgsConstructor
public class StudentInfoMapper {

  private final UserMapper userMapper;
  
  public StudentInfo mapStudentInfoRequestToStudentInfo(
      StudentInfoRequest studentInfoRequest, Note note,Double average){
    return StudentInfo.builder()
        .infoNote(studentInfoRequest.getInfoNote())
        .absentee(studentInfoRequest.getAbsentee())
        .midtermExam(studentInfoRequest.getMidtermExam())
        .finalExam(studentInfoRequest.getFinalExam())
        .examAverage(average)
        .letterGrade(note)
        .build();
  }
  
  public StudentInfoResponse mapStudentInfoToStudentInfoResponse(StudentInfo studentInfo){
    return StudentInfoResponse.builder()
        .lessonName(studentInfo.getLesson().getLessonName())
        .creditScore(studentInfo.getLesson().getCreditScore())
        .isCompulsory(studentInfo.getLesson().getIsCompulsory())
        .educationTerm(studentInfo.getEducationTerm().getTerm())
        .id(studentInfo.getId())
        .absentee(studentInfo.getAbsentee())
        .midtermExam(studentInfo.getMidtermExam())
        .finalExam(studentInfo.getFinalExam())
        .infoNote(studentInfo.getInfoNote())
        .note(studentInfo.getLetterGrade())
        .average(studentInfo.getExamAverage())
        .studentResponse(userMapper.mapUserToStudentResponse(studentInfo.getStudent()))
        .build();        
  }

  public StudentInfo mapStudentInfoUpdateRequestToStudentInfo(
      StudentInfoUpdateRequest studentInfoUpdateRequest, Long studentInfoId, Lesson lesson,
      EducationTerm educationTerm,
      Note note, Double noteAverage) {
    return StudentInfo.builder()
        .infoNote(studentInfoUpdateRequest.getInfoNote())
        .absentee(studentInfoUpdateRequest.getAbsentee())
        .midtermExam(studentInfoUpdateRequest.getMidtermExam())
        .finalExam(studentInfoUpdateRequest.getFinalExam())
        .examAverage(noteAverage)
        .letterGrade(note)
        .id(studentInfoId)
        .lesson(lesson)
        .educationTerm(educationTerm)
        .build();
  }
  
}
