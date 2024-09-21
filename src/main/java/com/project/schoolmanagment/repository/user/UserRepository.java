package com.project.schoolmanagment.repository.user;

import com.project.schoolmanagment.entity.concretes.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  
  boolean existsByUsername(String username);
  
  boolean existsByEmail(String email);
  
  boolean existsBySsn(String ssn);
  
  boolean existsByPhoneNumber(String phoneNumber);
  
  boolean existsBySsnOrEmailOrUsernameOrPhoneNumber(String ssn, String email, String username, String phoneNumber);

}
