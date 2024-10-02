package com.project.schoolmanagment.service.businnes;

import com.project.schoolmanagment.entity.concretes.business.LessonProgram;
import com.project.schoolmanagment.exception.ResourceNotFoundException;
import com.project.schoolmanagment.payload.messages.ErrorMessages;
import com.project.schoolmanagment.payload.response.businnes.ResponseMessage;
import com.project.schoolmanagment.repository.businnes.LessonProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonProgramService {

    private final LessonProgramRepository lessonProgramRepository;

    public  ResponseMessage<LessonProgram> getLessonById(Long id) {
        LessonProgram lessonProgram = lessonProgramRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.NOT_FOUND_LESSON_MESSAGE));

        return new ResponseMessage<>(lessonProgram);
    }
}

