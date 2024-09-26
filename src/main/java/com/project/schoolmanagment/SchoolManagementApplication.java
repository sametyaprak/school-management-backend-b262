package com.project.schoolmanagment;

import com.project.schoolmanagment.entity.concretes.user.UserRole;
import com.project.schoolmanagment.entity.enums.Gender;
import com.project.schoolmanagment.entity.enums.RoleType;
import com.project.schoolmanagment.payload.request.user.UserRequest;
import com.project.schoolmanagment.repository.user.UserRoleRepository;
import com.project.schoolmanagment.service.user.UserRoleService;
import com.project.schoolmanagment.service.user.UserService;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchoolManagementApplication implements CommandLineRunner {
  
  private final UserRoleService userRoleService;
  private final UserRoleRepository userRoleRepository;
  private final UserService userService;

  public SchoolManagementApplication(UserRoleService userRoleService,
      UserRoleRepository userRoleRepository, UserService userService) {
    this.userRoleService = userRoleService;
    this.userRoleRepository = userRoleRepository;
    this.userService = userService;
  }

  public static void main(String[] args) {
        SpringApplication.run(SchoolManagementApplication.class, args);
    }


  @Override
  public void run(String... args) throws Exception {
    if(userRoleService.getAllUserRoles().isEmpty()){
      //admin
      UserRole admin = new UserRole();
      admin.setRoleType(RoleType.ADMIN);
      admin.setRoleName(RoleType.ADMIN.getName());
      userRoleRepository.save(admin);
      //dean
      UserRole dean = new UserRole();
      dean.setRoleType(RoleType.MANAGER);
      dean.setRoleName(RoleType.MANAGER.getName());
      userRoleRepository.save(dean);
      //vice-dean
      UserRole videDean = new UserRole();
      videDean.setRoleType(RoleType.ASSISTANT_MANAGER);
      videDean.setRoleName(RoleType.ASSISTANT_MANAGER.getName());
      userRoleRepository.save(videDean);
      //student
      UserRole student = new UserRole();
      student.setRoleType(RoleType.STUDENT);
      student.setRoleName(RoleType.STUDENT.getName());
      userRoleRepository.save(student);
      //teacher
      UserRole teacher = new UserRole();
      teacher.setRoleType(RoleType.TEACHER);
      teacher.setRoleName(RoleType.TEACHER.getName());
      userRoleRepository.save(teacher);
    }
    if (userService.getAllUsers().isEmpty()){
      userService.saveUser(getUserRequest(),RoleType.ADMIN.name);
    }
  }
  
  private static UserRequest getUserRequest(){
    UserRequest userRequest = new UserRequest();
    userRequest.setUsername("admin");
    userRequest.setEmail("admin@admin.com");
    userRequest.setSsn("111-11-1111");
    userRequest.setPassword("Ankara06*");
    userRequest.setName("adminName");
    userRequest.setSurname("adminSurname");
    userRequest.setPhoneNumber("111-111-1111");
    userRequest.setGender(Gender.FEMALE);
    userRequest.setBirthDay(LocalDate.of(1980,1,1));
    userRequest.setBirthPlace("Texas");
    return userRequest;
  }
}









