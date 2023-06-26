package com.project.jose.security;

import com.project.jose.account.*;
import com.project.jose.account.UserRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private StudentRepo studentRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> adminOptional = adminRepo.findByUsername(username);
        Optional<Teacher> teacherOptional = teacherRepo.findByUsername(username);
        Optional<Student> studentOptional = studentRepo.findByUsername(username);

        UserDetailsImpl.UserDetailsImplBuilder userDetails = UserDetailsImpl.builder();
        if (adminOptional.isPresent()) {
            Admin admin = adminOptional.get();
            userDetails.id(admin.getId())
                    .username(admin.getUsername())
                    .email(admin.getEmail())
                    .password(admin.getPassword())
                    .authorities(List.of(new SimpleGrantedAuthority(UserRoleType.ADMIN.name())));
        } else if (teacherOptional.isPresent()) {
            Teacher teacher = teacherOptional.get();
            userDetails.id(teacher.getId())
                    .username(teacher.getUsername())
                    .email(teacher.getEmail())
                    .password(teacher.getPassword())
                    .authorities(List.of(new SimpleGrantedAuthority(UserRoleType.TEACHER.name())));
        } else if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            userDetails.id(student.getId())
                    .username(student.getUsername())
                    .email(student.getEmail())
                    .password(student.getPassword())
                    .authorities(List.of(new SimpleGrantedAuthority(UserRoleType.STUDENT.name())));
        } else {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }

        return userDetails.build();
    }

}
