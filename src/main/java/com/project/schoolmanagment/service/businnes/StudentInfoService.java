package com.project.schoolmanagment.service.businnes;

import com.project.schoolmanagment.entity.concretes.business.EducationTerm;
import com.project.schoolmanagment.entity.concretes.business.Lesson;
import com.project.schoolmanagment.entity.concretes.business.StudentInfo;
import com.project.schoolmanagment.entity.concretes.user.User;
import com.project.schoolmanagment.entity.enums.Note;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.exception.ConflictException;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.mappers.StudentInfoMapper;
import com.project.schoolmanagment.payload.messages.ErrorMessages;
import com.project.schoolmanagment.payload.messages.SuccessMessages;
import com.project.schoolmanagment.payload.request.businnes.StudentInfoRequest;
import com.project.schoolmanagment.payload.request.businnes.StudentInfoUpdateRequest;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.payload.response.businnes.StudentInfoResponse;
import com.project.schoolmanagment.repository.businnes.StudentInfoRepository;
import com.project.schoolmanagment.service.helper.MethodHelper;
import com.project.schoolmanagment.service.helper.PageableHelper;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  private final PageableHelper pageableHelper;
  
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

  public ResponseMessage<StudentInfoResponse> updateStudentInfo(
      StudentInfoUpdateRequest studentInfoUpdateRequest, Long id) {
    //validate existence of student info
    StudentInfo studentInfo = isStudentInfoExist(id);
    //validate existence of lesson
    Lesson lesson = lessonService.isLessonExistById(studentInfoUpdateRequest.getLessonId());
    //validate the existence of lesson program
    EducationTerm educationTerm = educationTermService.isEducationTermExist(studentInfoUpdateRequest.getEducationTermId());
    Double noteAverage = calculateAverage(studentInfoUpdateRequest.getMidtermExam(), studentInfoUpdateRequest.getFinalExam());
    Note note = checkLetterGrade(noteAverage);
    StudentInfo studentInfoToUpdate = studentInfoMapper.mapStudentInfoUpdateRequestToStudentInfo(
        studentInfoUpdateRequest,
        id,
        lesson,
        educationTerm,
        note,
        noteAverage);
    // each student can have only one student info entry related to a lesson
    if(!studentInfo.getLesson().getLessonName().equals(lesson.getLessonName())){
      validateLessonDuplication(studentInfo.getStudent().getId(),lesson.getLessonName());
    }
    // we should update teacher and student data
    studentInfoToUpdate.setStudent(studentInfo.getStudent());
    studentInfoToUpdate.setTeacher(studentInfo.getTeacher());
    StudentInfo updatedStudentInfo = studentInfoRepository.save(studentInfoToUpdate);
    return ResponseMessage.<StudentInfoResponse>builder()
        .message(SuccessMessages.STUDENT_INFO_UPDATE)
        .returnBody(studentInfoMapper.mapStudentInfoToStudentInfoResponse(updatedStudentInfo))
        .build();
  }

  /**
   * 
   * @param studentId to check if exist
   * @return StudentInfo entity that found
   */
  public StudentInfo isStudentInfoExist(Long studentId) {
    return studentInfoRepository.findById(studentId)
        .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.STUDENT_INFO_NOT_FOUND,studentId)));
  }

  public ResponseMessage deleteStudentInfoById(Long id) {
    isStudentInfoExist(id);
    studentInfoRepository.deleteById(id);
    return ResponseMessage.builder()
        .message(SuccessMessages.STUDENT_INFO_DELETE)
        .httpStatus(HttpStatus.OK)
        .build();
  }

  public List<StudentInfoResponse> findByStudentInfoByStudentId(Long studentId) {
    User student = methodHelper.isUserExist(studentId);
    methodHelper.checkRole(student,RoleType.STUDENT);
    return studentInfoRepository.findByStudentId(studentId)
        .stream()
        .map(studentInfoMapper::mapStudentInfoToStudentInfoResponse)
        .collect(Collectors.toList());    
  }

  public Page<StudentInfoResponse> findByTeacherOrStudentByPage(HttpServletRequest servletRequest, int page, int size) {
    Pageable pageable = pageableHelper.getPageable(page,size);
    String username = (String) servletRequest.getAttribute("username");
    User teacherOrStudent = methodHelper.loadByUsername(username);
    if(teacherOrStudent.getUserRole().getRoleType().equals(RoleType.STUDENT)){
      return studentInfoRepository.findByStudentUsername(username,pageable)
          .map(studentInfoMapper::mapStudentInfoToStudentInfoResponse);
    } else {
      return studentInfoRepository.findByTeacherUsername(username,pageable)
          .map(studentInfoMapper::mapStudentInfoToStudentInfoResponse);
    }
  }
}
