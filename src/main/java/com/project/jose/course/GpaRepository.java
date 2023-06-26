package com.project.jose.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GpaRepository extends JpaRepository<GPA, Long> {

    @Query("select g from gpa g where g.student.id = :id")
    List<GPA> listCourseGpaByStudentId(@Param("id") long id);

    @Query("select g from gpa g where g.course.id = :id")
    List<GPA> listGpaByCourseId(@Param("id") long id);
}
