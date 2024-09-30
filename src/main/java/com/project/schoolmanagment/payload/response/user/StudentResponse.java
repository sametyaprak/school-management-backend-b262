package com.project.schoolmanagment.payload.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.schoolmanagment.entity.concretes.business.LessonProgram;
import com.project.schoolmanagment.payload.request.abstracts.BaseUserRequest;
import com.project.schoolmanagment.payload.response.abstracts.AbstractUserResponse;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentResponse extends AbstractUserResponse {
  
  private Set<LessonProgram>lessonProgramSet;
  private int studentNumber;
  private String motherName;
  private String fatherName;
  private boolean isActive;

}
