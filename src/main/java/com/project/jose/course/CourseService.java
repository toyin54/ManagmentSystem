package com.project.jose.course;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "Course", description = "Everything about your Course")
@Log4j2
public class CourseService {

    @Autowired
    private CourseRepository repo;

    @GetMapping
    @Operation(summary = "Returns all the Course in the database")
    @ApiResponse(responseCode = "200", description = "valid response",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Course.class))})
    public List<Course> list() {
        log.traceEntry("Enter list");
        var retval = repo.findAll();
        log.traceExit("Exit list", retval);
        return repo.findAll();
    }

    @PostMapping("/save")
    @Operation(summary = "Save the Course and returns the Course id")
    public long save(Course course) {
        log.traceEntry("enter save", course);
        repo.save(course);
        log.traceExit("exit save", course);
        return course.getId();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete the Course")
    public void delete(long id) {
        log.traceEntry("Enter delete", id);
        repo.deleteById(id);
        log.traceExit("Exit delete");
    }

    @GetMapping("/search")
    @Operation(summary = "Search courses by keyword")
    public List<Course> search(@RequestParam String keyword,
                               @RequestParam(required = false) Integer year,
                               @RequestParam(required = false) String quarter) {
        log.traceEntry("Enter search", keyword);

        Quarter realQuarter = Quarter.valueOfIgnoreCase(quarter);
        String[] keywords = keyword.split(" ");
        List<Course> courses = Arrays.stream(keywords)
                .parallel()
                .flatMap(kw -> {
                    if (year == null && realQuarter == null) {
                        return repo.search(kw).stream();
                    } else if (year == null) {
                        return repo.search(kw, realQuarter).stream();
                    } else if (quarter == null) {
                        return repo.search(kw, year).stream();
                    } else {
                        return repo.search(kw, year, realQuarter).stream();
                    }
                })
                .distinct()
                .collect(Collectors.toList());

        log.traceExit("Exit search", courses);
        return courses;
    }
}
