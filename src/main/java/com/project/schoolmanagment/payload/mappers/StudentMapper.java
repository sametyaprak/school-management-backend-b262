package com.project.schoolmanagment.payload.mappers;

import com.project.schoolmanagment.entity.concretes.user.User;
import com.project.schoolmanagment.payload.response.user.StudentResponse;

public class StudentMapper {

    public StudentResponse mapUserToStudentResponse(User student){
        return StudentResponse.builder()
                .id(student.getId())
                .username(student.getUsername())
                .name(student.getName())
                .surname(student.getSurname())
                .birthday(student.getBirthday())
                .ssn(student.getSsn())
                .birthPlace(student.getBirthplace())
                .phoneNumber(student.getPhoneNumber())
                .gender(student.getGender())
                .email(student.getEmail())
                .studentNumber(student.getStudentNumber())
                .motherName(student.getMotherName())
                .fatherName(student.getFatherName())
                .lessonProgramSet(student.getLessonProgramList())
                .isActive(student.isActive())
                .build();
    }
}
