package tests;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.project.schoolmanagment.entity.concretes.business.LessonProgram;
import com.project.schoolmanagment.entity.enums.Day;
import com.project.schoolmanagment.exception.BadRequestException;
import com.project.schoolmanagment.service.validator.TimeValidator;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class TimeValidatorTest {
  
  private final TimeValidator timeValidator = new TimeValidator();
  
  
  @Test
  void testNoDuplicateLessonPrograms(){
    Set<LessonProgram>lessonPrograms = new HashSet<>();
    lessonPrograms.add(new LessonProgram(Day.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0)));
    lessonPrograms.add(new LessonProgram(Day.MONDAY, LocalTime.of(11, 0), LocalTime.of(12, 0)));
    timeValidator.checkDuplicateLessonProgram(lessonPrograms);
  }
  @Test
  void testDuplicateStartTimeThrowsException() {
    Set<LessonProgram> lessonPrograms = new HashSet<>();
    lessonPrograms.add(new LessonProgram(Day.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0)));
    lessonPrograms.add(new LessonProgram(Day.MONDAY, LocalTime.of(9, 0), LocalTime.of(11, 0)));

    assertThrows(BadRequestException.class, () -> timeValidator.checkDuplicateLessonProgram(lessonPrograms));
  }

  @Test
  void testOverlappingTimesThrowsException() {
    Set<LessonProgram> lessonPrograms = new HashSet<>();
    lessonPrograms.add(new LessonProgram(Day.MONDAY, LocalTime.of(9, 0), LocalTime.of(11, 0)));
    lessonPrograms.add(new LessonProgram(Day.MONDAY, LocalTime.of(10, 0), LocalTime.of(12, 0)));

    assertThrows(BadRequestException.class, () -> timeValidator.checkDuplicateLessonProgram(lessonPrograms));
  }

  @Test
  void testOverlappingStopTimesThrowsException() {
    Set<LessonProgram> lessonPrograms = new HashSet<>();
    lessonPrograms.add(new LessonProgram(Day.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0)));
    lessonPrograms.add(new LessonProgram(Day.MONDAY, LocalTime.of(8, 30), LocalTime.of(9, 30)));

    assertThrows(BadRequestException.class, () -> timeValidator.checkDuplicateLessonProgram(lessonPrograms));
  }

  @Test
  void testAdjacentLessonProgramsNoException() {
    Set<LessonProgram> lessonPrograms = new HashSet<>();
    lessonPrograms.add(new LessonProgram(Day.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0)));
    lessonPrograms.add(new LessonProgram(Day.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 0)));

    timeValidator.checkDuplicateLessonProgram(lessonPrograms);
  }

}
