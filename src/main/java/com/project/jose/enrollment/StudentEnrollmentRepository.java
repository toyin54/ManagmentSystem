package com.project.jose.enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, Long> {
}
