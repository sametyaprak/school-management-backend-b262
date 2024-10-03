package com.project.schoolmanagment.repository.businnes;

import com.project.schoolmanagment.entity.concretes.business.Meeting;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting,Long> {

  
  List<Meeting> findByStudentList_IdEquals(Long studentId);


  List<Meeting>getByAdvisoryTeacher_IdEquals(Long id);

  Page<Meeting> getByAdvisoryTeacher_IdEquals(Long id, Pageable pageable);
  
}
