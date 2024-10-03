package com.project.schoolmanagment.service.user;

import com.project.schoolmanagment.entity.concretes.business.LessonProgram;
import com.project.schoolmanagment.entity.concretes.user.User;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.payload.mappers.UserMapper;
import com.project.schoolmanagment.payload.messages.SuccessMessages;
import com.project.schoolmanagment.payload.request.businnes.AddLessonProgram;
import com.project.schoolmanagment.payload.request.user.TeacherRequest;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.payload.response.user.StudentResponse;
import com.project.schoolmanagment.payload.response.user.UserResponse;
import com.project.schoolmanagment.repository.user.UserRepository;
import com.project.schoolmanagment.service.businnes.LessonProgramService;
import com.project.schoolmanagment.service.helper.MethodHelper;
import com.project.schoolmanagment.service.helper.PageableHelper;
import com.project.schoolmanagment.service.validator.UniquePropertyValidator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {
  
  private final UserRepository userRepository;
  private final UserRoleService userRoleService;
  private final UserMapper userMapper;
  private final LessonProgramService lessonProgramService;
  private final UniquePropertyValidator uniquePropertyValidator;
  private final MethodHelper methodHelper;
  private final UserService userService;
  private final PageableHelper pageableHelper;

  public ResponseMessage<UserResponse> save(TeacherRequest teacherRequest) {

    Set<LessonProgram>lessonProgramSet = lessonProgramService.getLessonProgramById(teacherRequest.getLessonProgramIdList());
    //validate unique props.
    uniquePropertyValidator.checkDuplicate(
        teacherRequest.getUsername(),
        teacherRequest.getSsn(),
        teacherRequest.getPhoneNumber(),
        teacherRequest.getEmail());
    
    User teacher = userMapper.mapUserRequestToUser(teacherRequest, RoleType.TEACHER.getName());
    //set additional properties for teacher
    teacher.setIsAdvisor(teacherRequest.getIsAdvisoryTeacher());
    teacher.setLessonProgramList(lessonProgramSet);
    User savedTeacher = userRepository.save(teacher);
    
    return ResponseMessage.<UserResponse>builder()
        .message(SuccessMessages.TEACHER_SAVE)
        .httpStatus(HttpStatus.CREATED)
        .returnBody(userMapper.mapUserToUserResponse(savedTeacher))
        .build();    
  }

  public ResponseMessage<UserResponse> updateTeacher(TeacherRequest teacherRequest, Long userId) {
    //validate if teacher exists
    User teacher = methodHelper.isUserExist(userId);
    //validate id user is a teacher
    methodHelper.checkRole(teacher,RoleType.TEACHER);
    //validate unique props.
    uniquePropertyValidator.checkUniqueProperties(teacher,teacherRequest);
    Set<LessonProgram>lessonPrograms = lessonProgramService.getLessonProgramById(teacherRequest.getLessonProgramIdList());
    User teacherToUpdate = userMapper.mapUserRequestToUser(teacherRequest, RoleType.TEACHER.getName());
    teacherToUpdate.setId(teacher.getId());
    teacherToUpdate.setIsAdvisor(teacherRequest.getIsAdvisoryTeacher());
    teacherToUpdate.setLessonProgramList(lessonPrograms);
    User savedTeacher = userRepository.save(teacherToUpdate);
    return ResponseMessage.<UserResponse>builder()
        .message(SuccessMessages.TEACHER_UPDATE)
        .returnBody(userMapper.mapUserToUserResponse(savedTeacher))
        .httpStatus(HttpStatus.OK)
        .build();
  }

  public ResponseMessage<UserResponse> addLessonProgram(AddLessonProgram lessonProgram) {
    User teacher = methodHelper.isUserExist(lessonProgram.getTeacherId());
    methodHelper.checkRole(teacher,RoleType.TEACHER);
    Set<LessonProgram>lessonPrograms = lessonProgramService.getLessonProgramById(lessonProgram.getLessonProgramId());
    teacher.getLessonProgramList().addAll(lessonPrograms);
    //update teacher in DB
    User updatedTeacher = userRepository.save(teacher);
    return ResponseMessage.<UserResponse>builder()
        .message(SuccessMessages.TEACHER_UPDATE)
        .returnBody(userMapper.mapUserToUserResponse(updatedTeacher))
        .httpStatus(HttpStatus.OK)
        .build();
    
  }

  public ResponseMessage<UserResponse> deleteById(Long id) {
    User teacher = methodHelper.isUserExist(id);
    methodHelper.checkRole(teacher,RoleType.TEACHER);
    List<User>allStudents = userRepository.findByAdvisorTeacherId(id);
    if(!allStudents.isEmpty()){
      allStudents.forEach(students -> students.setAdvisorTeacherId(null));
    }
    userRepository.deleteById(id);
    return ResponseMessage.<UserResponse>builder()
        .message(SuccessMessages.ADVISOR_TEACHER_DELETE)
        .returnBody(userMapper.mapUserToUserResponse(teacher))
        .httpStatus(HttpStatus.OK)
        .build();
  }

  public List<UserResponse> getAllTeacher() {
    return userRepository.findAllByUserRole(userRoleService.getUserRole(RoleType.TEACHER))
        .stream()
        .map(userMapper::mapUserToUserResponse)
        .collect(Collectors.toList());    
  }

  public Page<UserResponse> getTeachersByPage(int page, int size, String sort, String type) {
    return userRepository.findByUserByRole(
        userRoleService.getUserRole(RoleType.TEACHER).getRoleName(),
        pageableHelper.getPageable(page, size, sort, type))
        .map(userMapper::mapUserToUserResponse);
  }

  public List<StudentResponse> getAllStudentByAdvisorTeacher(
      HttpServletRequest httpServletRequest) {
    String username = (String) httpServletRequest.getAttribute("username");
    User teacher = methodHelper.loadByUsername(username);
    methodHelper.checkIsAdvisor(teacher);
    return userRepository.findByAdvisorTeacherId(teacher.getId())
        .stream()
        .map(userMapper::mapUserToStudentResponse)
        .collect(Collectors.toList());
  }
}
