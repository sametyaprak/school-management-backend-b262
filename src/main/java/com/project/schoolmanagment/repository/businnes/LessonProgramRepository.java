package com.project.schoolmanagment.repository.businnes;

import com.project.schoolmanagment.entity.concretes.business.LessonProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonProgramRepository extends JpaRepository<LessonProgram, LessonProgram> {
  
  

}
