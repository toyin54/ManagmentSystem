package com.project.jose.course;

import com.project.jose.account.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "gpa")
@AllArgsConstructor
@NoArgsConstructor
public class GPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
    private float grade;
    private float gpa;

    public GPA(Course course, Student student, float grade, float gpa) {
        this.course = course;
        this.student = student;
        this.grade = grade;
        this.gpa = gpa;
    }
}
