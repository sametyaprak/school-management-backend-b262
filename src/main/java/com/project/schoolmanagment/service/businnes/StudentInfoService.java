package com.project.schoolmanagment.service.businnes;

import com.project.schoolmanagment.entity.concretes.business.EducationTerm;
import com.project.schoolmanagment.entity.concretes.business.Lesson;
import com.project.schoolmanagment.entity.concretes.business.StudentInfo;
import com.project.schoolmanagment.entity.concretes.user.User;
import com.project.schoolmanagment.entity.enums.Note;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exception.ConflictException;
import com.project.schoolmanagment.payload.mappers.StudentInfoMapper;
import com.project.schoolmanagment.payload.messages.ErrorMessages;
import com.project.schoolmanagment.payload.messages.SuccessMessages;
import com.project.schoolmanagment.payload.request.businnes.StudentInfoRequest;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.payload.response.businnes.StudentInfoResponse;
import com.project.schoolmanagment.repository.businnes.StudentInfoRepository;
import com.project.schoolmanagment.service.helper.MethodHelper;
import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentInfoService {
  
  private final MethodHelper methodHelper;
  private final LessonService lessonService;
  private final EducationTermService educationTermService;
  private final StudentInfoRepository studentInfoRepository;
  private final StudentInfoMapper studentInfoMapper;
  
  @Value("${midterm.exam.impact.percentage}")
  private Double midtermPercentage;

  @Value("${final.exam.impact.percentage}")
  private Double finalPercentage;

  public ResponseMessage<StudentInfoResponse> save(HttpServletRequest httpServletRequest,
      StudentInfoRequest studentInfoRequest) {
    String teacherUsername = (String) httpServletRequest.getAttribute("username");
    //validate student id
    User student = methodHelper.isUserExist(studentInfoRequest.getStudentId());
    //validate user if student
    methodHelper.checkRole(student, RoleType.STUDENT);
    User teacher = methodHelper.loadByUsername(teacherUsername);
    //validate and fetch a lesson
    Lesson lesson = lessonService.isLessonExistById(studentInfoRequest.getLessonId());
    //validate and fetch education term
    EducationTerm educationTerm = educationTermService.isEducationTermExist(
        studentInfoRequest.getEducationTermId());
    //student should have only one student info for a lesson
    validateLessonDuplication(studentInfoRequest.getStudentId(), lesson.getLessonName());
    Note note = checkLetterGrade(
        calculateAverage(studentInfoRequest.getMidtermExam(),
                          studentInfoRequest.getFinalExam()));
    //map DTO to entity
    StudentInfo studentInfo = studentInfoMapper.mapStudentInfoRequestToStudentInfo(
        studentInfoRequest,
        note,
        calculateAverage(studentInfoRequest.getMidtermExam(), studentInfoRequest.getFinalExam()));
    //set missing properties
    studentInfo.setStudent(student);
    studentInfo.setEducationTerm(educationTerm);
    studentInfo.setTeacher(teacher);
    studentInfo.setLesson(lesson);
    StudentInfo savedStudentInfo = studentInfoRepository.save(studentInfo);
    return ResponseMessage.<StudentInfoResponse>builder()
        .message(SuccessMessages.STUDENT_INFO_SAVE)
        .returnBody(studentInfoMapper.mapStudentInfoToStudentInfoResponse(savedStudentInfo))
        .httpStatus(HttpStatus.OK)
        .build();
  }
  
  
  private void validateLessonDuplication(Long studentId, String lessonName){
    if(studentInfoRepository.isStudentInfoForLessonDuplicated(studentId,lessonName)){
      throw new ConflictException(
          String.format(ErrorMessages.ALREADY_REGISTER_LESSON_MESSAGE,lessonName));
    }
  }
  
  private Double calculateAverage (Double midterm,Double finalExam){
    return (midterm*midtermPercentage)+(finalExam*finalPercentage);
  }

  private Note checkLetterGrade(Double average){
    if(average<50.0) {
      return Note.FF;
    } else if (average<60) {
      return Note.DD;
    } else if (average<65) {
      return Note.CC;
    } else if (average<70) {
      return  Note.CB;
    } else if (average<75) {
      return  Note.BB;
    } else if (average<80) {
      return Note.BA;
    } else {
      return Note.AA;
    }
  }
  
}
