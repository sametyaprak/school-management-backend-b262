package com.project.schoolmanagment.repository.businnes;

import com.project.schoolmanagment.entity.concretes.business.EducationTerm;
import com.project.schoolmanagment.entity.enums.Term;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationTermRepository extends JpaRepository<EducationTerm, Long> {
  
  @Query("select (count (e) > 0) from EducationTerm e where e.term=?1 and extract(year from e.startDate) =?2 ")
  boolean existsByTermAndYear(Term term, int year);  
  
  @Query("select e from EducationTerm e where extract(year from e.startDate) =?1 ")
  List<EducationTerm>findByYear(int year);

}
