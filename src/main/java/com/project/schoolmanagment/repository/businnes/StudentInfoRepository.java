package com.project.schoolmanagment.repository.businnes;

import com.project.schoolmanagment.entity.concretes.business.StudentInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentInfoRepository extends JpaRepository<StudentInfo,Long> {
  
  @Query("select (count (s)>0) from StudentInfo s where s.student.id= ?1 and s.lesson.lessonName = ?2")
  boolean isStudentInfoForLessonDuplicated(Long studentId, String lessonName);
  


}
