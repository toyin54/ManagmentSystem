package com.project.jose.course;

import com.project.jose.account.Student;
import com.project.jose.account.StudentRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gpa")
@Tag(name = "GPA", description = "Everything about course GPA")
@Log4j2
public class GpaService {

    @Autowired
    private GpaRepository gpaRepository;
    @Autowired
    private StudentRepo studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    @GetMapping
    @Operation(summary = "Returns all the GPA in the database")
    @ApiResponse(responseCode = "200", description = "valid response",
            content = {@Content(mediaType="application/json", schema=@Schema(implementation=GPA.class))})
    public List<GPA> list() {
        log.traceEntry("Enter list");
        var retval = gpaRepository.findAll();
        log.traceExit("Exit list", retval);
        return gpaRepository.findAll();
    }

    @PostMapping("/queryByStudentId")
    @Operation(summary = "Query and list the GPA of all courses enrolled by the student.")
    public List<GPA> queryByStudentId(long id) {
        log.traceEntry("Enter list");
        var retval = gpaRepository.listCourseGpaByStudentId(id);
        log.traceExit("Exit list", retval);
        return retval;
    }

    @PostMapping("/queryByCourseId")
    @Operation(summary = "Query and list the GPA of all students under this course.")
    public List<GPA> queryByCourseId(long id) {
        log.traceEntry("Enter list");
        var retval = gpaRepository.listGpaByCourseId(id);
        log.traceExit("Exit list", retval);
        return retval;
    }

    @PostMapping("/save")
    @Operation(summary = "Save the GPA and returns the GPA id")
    public long save(long studentId, long courseId, float grade, float gpa) {
        log.traceEntry("enter save", studentId, courseId, grade, gpa);
        Course course = courseRepository.findById(courseId).orElseThrow();
        Student student = studentRepository.findById(studentId).orElseThrow();
        GPA retval = gpaRepository.save(new GPA(course, student, grade, gpa));
        log.traceExit("exit save");
        return retval.getId();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete the GPA")
    public void delete(long id) {
        log.traceEntry("Enter delete", id);
        gpaRepository.deleteById(id);
        log.traceExit("Exit delete");
    }
}
