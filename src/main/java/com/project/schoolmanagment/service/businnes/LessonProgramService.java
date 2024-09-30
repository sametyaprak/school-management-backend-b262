package com.project.schoolmanagment.service.businnes;

import com.project.schoolmanagment.repository.businnes.LessonProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonProgramService {
  
  private final LessonProgramRepository lessonProgramRepository;

}
