package com.project.schoolmanagment.repository.businnes;

import com.project.schoolmanagment.entity.concretes.business.LessonProgram;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonProgramRepository extends JpaRepository<LessonProgram, LessonProgram> {

  List<LessonProgram> findByUsers_IdNull();

  List<LessonProgram> findByUsers_IdNotNull();


  @Query("SELECT l FROM LessonProgram l WHERE l.id IN :idSet")
  Set<LessonProgram>getLessonProgramByIdList(Set<Long>idSet);

}
