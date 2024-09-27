package com.project.schoolmanagment.payload.request.businnes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.schoolmanagment.entity.enums.Term;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EducationTermRequest {

  @NotNull(message = "Education Term must not be empty")
  private Term term;

  @NotNull(message ="Start Date must not be empty")
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  @NotNull(message ="End Date must not be empty")
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate endDate;

  @NotNull(message ="Last Registration Date must not be empty")
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate lastRegistrationDate;
  
}
