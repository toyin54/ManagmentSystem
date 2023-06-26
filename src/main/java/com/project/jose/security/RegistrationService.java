package com.project.jose.security;

import com.project.jose.account.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Profile("security-register")
public class RegistrationService {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private PasswordEncoder encoder;


    @PostMapping("/signup")
    public String registerUser(SignupRequest signupRequest) {
        String username = signupRequest.getUsername();
        if (adminRepo.existsByUsername(username)
                || teacherRepo.existsByUsername(username)
                || studentRepo.existsByUsername(username)) {
            return "Error: Username is already taken!";
        }

        String email = signupRequest.getEmail();
        if (adminRepo.existsByEmail(email)
                || teacherRepo.existsByEmail(email)
                || studentRepo.existsByEmail(email)) {
            return "Error: Email is already in use!";
        }

        String encodedPassword = encoder.encode(signupRequest.getPassword());
        switch (signupRequest.getRole()) {
            case ADMIN -> {
                Admin admin = new Admin();
                admin.setUsername(username);
                admin.setEmail(email);
                admin.setPassword(encodedPassword);
                adminRepo.save(admin);
            }
            case TEACHER -> {
                Teacher teacher = new Teacher();
                teacher.setUsername(username);
                teacher.setEmail(email);
                teacher.setPassword(encodedPassword);
                teacherRepo.save(teacher);
            }
            case STUDENT -> {
                Student student = new Student();
                student.setUsername(username);
                student.setEmail(email);
                student.setPassword(encodedPassword);
                studentRepo.save(student);
            }
        }

        return "User registered successfully!";
    }
}
