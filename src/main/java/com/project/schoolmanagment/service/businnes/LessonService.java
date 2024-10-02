package com.project.schoolmanagment.service.businnes;

import com.project.schoolmanagment.entity.concretes.business.Lesson;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.messages.ErrorMessages;

import com.project.schoolmanagment.repository.businnes.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {

  private final LessonRepository lessonRepository;
  public Set<Lesson> getLessonByIdSet(Set<Long> idSet) {
    return idSet.stream()
            .map(this::findLessonById)  // Fetch each lesson by ID
            .collect(Collectors.toSet());  // Collect directly if LessonResponse can be constructed from Lesson
  }

  private Lesson findLessonById(Long id) {
    return lessonRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.NOT_FOUND_LESSON_MESSAGE));
  }
}
