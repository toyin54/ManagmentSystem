package com.project.jose.webcontroller;

import com.project.jose.account.Student;
import com.project.jose.account.StudentRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String list(Model model, HttpSession session) {
        model.addAttribute("students", studentRepo.findAll());
        if (session.getAttribute("student") == null) {
            model.addAttribute("student", new Student());
            model.addAttribute("btnAddOrModifyLabel", "Add");
        } else {
            model.addAttribute("student", session.getAttribute("student"));
            model.addAttribute("btnAddOrModifyLabel", "Modify");
        }
        return "student/list_students";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String createStudentForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "student/create_student";
    }

    @PostMapping("/new")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String createStudent(@ModelAttribute("student") Student student) {
        if (!studentRepo.existsByUsername(student.getUsername()) && !studentRepo.existsByEmail(student.getEmail())) {
            student.setPassword(encoder.encode(student.getPassword()));
            studentRepo.save(student);
        }
        return "redirect:/student/list";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String get(@PathVariable("id") Long id, Model model, HttpSession session) {
        Student student = studentRepo.findById(id).get();
        student.setPassword("");
        model.addAttribute("student", student);
        return "student/edit_student";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String editStudent(@PathVariable("id") Long id, @ModelAttribute("course") Student student) {
        Optional<Student> optionalStudent = studentRepo.findById(id);
        if (optionalStudent.isPresent()) {
            Student oriStudent = optionalStudent.get();
            oriStudent.setFirstName(student.getFirstName());
            oriStudent.setLastName(student.getLastName());
            oriStudent.setGender(student.getGender());
            oriStudent.setDob(student.getDob());

            if (StringUtils.hasLength(student.getUsername())) {
                oriStudent.setUsername(student.getUsername());
            }
            if (StringUtils.hasLength(student.getEmail())) {
                oriStudent.setEmail(student.getEmail());
            }
            if (StringUtils.hasLength(student.getPassword())) {
                oriStudent.setPassword(encoder.encode(student.getPassword()));
            }
            studentRepo.save(oriStudent);
        }
        return "redirect:/student/list";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String delete(@PathVariable("id") Long id, Model model, HttpSession session) {
        studentRepo.deleteById(id);
        return "redirect:/student/list";
    }


}


