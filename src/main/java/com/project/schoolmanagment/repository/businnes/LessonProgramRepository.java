package com.project.schoolmanagment.repository.businnes;

import com.project.schoolmanagment.entity.concretes.business.LessonProgram;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonProgramRepository extends JpaRepository<LessonProgram, LessonProgram> {

  List<LessonProgram> findByUsers_IdNull();

  List<LessonProgram> findByUsers_IdNotNull();

}
