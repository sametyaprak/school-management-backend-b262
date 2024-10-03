package com.project.schoolmanagment.payload.request.businnes;

import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddLessonProgram {

  @NotNull(message = "Please select lesson program")
  @Size(min = 1, message = "Lessons must not be empty")
  private Set<Long> lessonProgramId;

  @NotNull(message = "Please select teacher")
  private Long teacherId;

}
