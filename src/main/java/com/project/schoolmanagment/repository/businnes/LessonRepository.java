package com.project.schoolmanagment.repository.businnes;

import com.project.schoolmanagment.entity.concretes.business.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {  

}
