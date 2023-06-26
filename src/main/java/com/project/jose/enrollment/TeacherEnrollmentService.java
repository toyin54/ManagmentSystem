package com.project.jose.enrollment;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher-enrollment")
@Tag(name = "TeacherEnrollment", description = "Courses being teached by the teachers")
@Log4j2
public class TeacherEnrollmentService {

    @Autowired
    private TeacherEnrollmentRepository repo;

    @GetMapping
    public List<TeacherEnrollment> list() {
        log.traceEntry("Enter list");
        var retval = repo.findAll();
        log.traceExit("Exit list", retval);
        return retval;
    }

    @PostMapping
    public TeacherEnrollment save(@RequestBody TeacherEnrollment teacher) {
        log.traceEntry("enter save", teacher);
        repo.save(teacher);
        log.traceExit("exit save", teacher);
        return teacher;
    }

    @PostMapping("/valid")
    public ResponseEntity<TeacherEnrollment> saveValidated(@Valid @RequestBody TeacherEnrollment teacher) {
        log.traceEntry("enter save", teacher);
        repo.save(teacher);
        log.traceExit("exit save", teacher);
        return ResponseEntity.ok(teacher);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id ) {
        log.traceEntry("Enter delete", id);
        repo.deleteById(id);
        log.traceExit("Exit delete");
    }
}
