package com.project.schoolmanagment.repository.businnes;

import com.project.schoolmanagment.entity.concretes.business.StudentInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentInfoRepository extends JpaRepository<StudentInfo,Long> {
  


}
