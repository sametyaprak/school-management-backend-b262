package com.project.schoolmanagment.payload.response.businnes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.schoolmanagment.entity.concretes.business.EducationTerm;
import com.project.schoolmanagment.entity.concretes.business.Lesson;
import com.project.schoolmanagment.entity.enums.Day;
import com.project.schoolmanagment.payload.response.user.StudentResponse;
import com.project.schoolmanagment.payload.response.user.TeacherResponse;
import java.time.LocalTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonProgramResponse {
  
  private Long lessonProgramId;
  private Day day;
  private LocalTime startTime;
  private LocalTime stopTime;
  private Set<Lesson>lessonName;
  private EducationTerm educationTerm;
  private Set<StudentResponse>student;
  private Set<TeacherResponse>teachers;
  

}
