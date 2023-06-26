package com.project.jose.course;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "courses")
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dept;
    private String num;
    private String name;

    @Column(name = "academic_year")
    private int year;
    @Enumerated(EnumType.STRING)
    private Quarter quarter;

    public Course(String dept, String num, String name, int year, Quarter quarter) {
        this.dept = dept;
        this.num = num;
        this.name = name;
        this.year = year;
        this.quarter = quarter;
    }
}
