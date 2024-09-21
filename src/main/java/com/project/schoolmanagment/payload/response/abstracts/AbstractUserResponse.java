package com.project.schoolmanagment.payload.response.abstracts;

import com.project.schoolmanagment.entity.enums.Gender;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractUserResponse {
  
  private Long id;
  private String username;
  private String name;
  private String surname;
  private LocalDate birthday;
  private String ssn;
  private String birthPlace;
  private String phoneNumber;
  private Gender gender;
  private String email;
  private String userRole;

}
