package com.project.schoolmanagment.service.user;

import com.project.schoolmanagment.entity.concretes.user.User;
import com.project.schoolmanagment.entity.concretes.user.UserRole;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.payload.mappers.UserMapper;
import com.project.schoolmanagment.payload.messages.SuccessMessages;
import com.project.schoolmanagment.payload.request.user.StudentRequest;
import com.project.schoolmanagment.payload.request.user.StudentUpdateRequest;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.payload.response.user.StudentResponse;
import com.project.schoolmanagment.repository.user.UserRepository;
import com.project.schoolmanagment.service.helper.MethodHelper;
import com.project.schoolmanagment.service.validator.UniquePropertyValidator;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {
  
  private final MethodHelper methodHelper;
  private final UniquePropertyValidator uniquePropertyValidator;
  private final UserMapper userMapper;
  private final UserRepository userRepository;

  public ResponseMessage<StudentResponse> saveStudent(StudentRequest studentRequest) {
    //do we really have a user with this ID
    User advisorTeacher = methodHelper.isUserExist(studentRequest.getAdvisorTeacherId());
    // check if this teacher is really advisor teacher
    methodHelper.checkIsAdvisor(advisorTeacher);
    //validate unique properties
    uniquePropertyValidator.checkDuplicate(
        studentRequest.getUsername(),
        studentRequest.getSsn(),
        studentRequest.getPhoneNumber(),
        studentRequest.getEmail());
    //map
    User student = userMapper.mapUserRequestToUser(studentRequest, RoleType.STUDENT.getName());
    //set missing properties
    student.setAdvisorTeacherId(advisorTeacher.getId());
    student.setActive(true);
    student.setBuiltIn(false);
    //every student will have a student number and this number start with 1000.
    student.setStudentNumber(getLastNumber());
    return ResponseMessage.<StudentResponse>builder()
        .returnBody(userMapper.mapUserToStudentResponse(userRepository.save(student)))
        .message(SuccessMessages.STUDENT_SAVE)
        .httpStatus(HttpStatus.CREATED)
        .build();      
  }
  
  private int getLastNumber(){
    if(!userRepository.findStudent(RoleType.STUDENT)){
      //first student
      return 1000;
    }
    return userRepository.getMaxStudentNumber() + 1;
  }


  public ResponseEntity<String> updateStudent(StudentUpdateRequest studentUpdateRequest,
      HttpServletRequest httpServletRequest) {
    String username = (String) httpServletRequest.getAttribute("username");
    //check if user exists
    User student = methodHelper.loadByUsername(username);
    //handle duplications
    uniquePropertyValidator.checkUniqueProperties(student,studentUpdateRequest);
    User studentToSave = userMapper.mapStudentUpdateRequestToUser(studentUpdateRequest);
    studentToSave.setId(student.getId());
    studentToSave.setPassword(student.getPassword());
    studentToSave.setBuiltIn(student.getBuiltIn());
    userRepository.save(studentToSave);
    return ResponseEntity.ok(SuccessMessages.STUDENT_UPDATE);   
  }

  public ResponseMessage<StudentResponse> updateStudentByManager(Long userId,
      StudentRequest studentRequest) {
    //validate user existence
    User student = methodHelper.isUserExist(userId);
    methodHelper.checkRole(student,RoleType.STUDENT);
    uniquePropertyValidator.checkUniqueProperties(student,studentRequest);
    User studentToUpdate = userMapper.mapUserRequestToUser(studentRequest,RoleType.STUDENT.getName());
    //missing properties
    studentToUpdate.setId(student.getId());
    studentToUpdate.setStudentNumber(student.getStudentNumber());
    studentToUpdate.setActive(student.isActive());
    return ResponseMessage.<StudentResponse>builder()
        .message(SuccessMessages.STUDENT_UPDATE)
        .returnBody(userMapper.mapUserToStudentResponse(userRepository.save(studentToUpdate)))
        .httpStatus(HttpStatus.OK)
        .build();    
  }
}
