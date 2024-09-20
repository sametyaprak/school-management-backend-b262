package com.project.schoolmanagment.entity.concretes.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.schoolmanagment.entity.concretes.user.User;
import com.project.schoolmanagment.entity.enums.Note;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  private Integer absentee;
  
  private Double midtermExam;
  
  private Double finalExam;
  
  private String infoNote;
  
  private Double examAverage;
  
  @Enumerated(EnumType.STRING)
  private Note letterGrade;
  
  @JsonIgnore
  @ManyToOne
  private User teacher;
  
  @JsonIgnore
  @ManyToOne
  private User student;
  
  @ManyToOne
  private Lesson lesson;
  
  @OneToOne
  private EducationTerm educationTerm;
  
  


}
