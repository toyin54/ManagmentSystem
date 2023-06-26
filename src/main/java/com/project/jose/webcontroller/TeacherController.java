package com.project.jose.webcontroller;

import com.project.jose.account.Teacher;
import com.project.jose.account.TeacherRepo;
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
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherRepo repo;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("list")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String list(Model model, HttpSession session) {
        model.addAttribute("teachers", repo.findAll());
        if (session.getAttribute("teachers") == null) {
            model.addAttribute("teacher", new Teacher());
            model.addAttribute("btnAddOrModifyLabel", "Add");
        } else {
            model.addAttribute("teacher", session.getAttribute("teacher"));
            model.addAttribute("btnAddOrModifyLabel", "Modify");
        }
        return "teacher/list_teachers";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String createTeacherForm(Model model) {
        Teacher teacher = new Teacher();
        model.addAttribute("teacher", teacher);
        return "teacher/create_teacher";

    }

    @PostMapping("/new")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String createTeacher(@ModelAttribute("teacher") Teacher teacher) {
        if (!repo.existsByUsername(teacher.getUsername()) && !repo.existsByEmail(teacher.getEmail())) {
            teacher.setPassword(encoder.encode(teacher.getPassword()));
            repo.save(teacher);
        }
        return "redirect:/teacher/list";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String get(@PathVariable("id") Long id, Model model, HttpSession session) {
        Teacher teacher = repo.findById(id).get();
        teacher.setPassword("");
        model.addAttribute("teacher", teacher);
        return "teacher/edit_teacher";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String editTeacher(@PathVariable("id") Long id, @ModelAttribute("course") Teacher te) {
        Optional<Teacher> optionalTeacher = repo.findById(id);
        if (optionalTeacher.isPresent()) {
            Teacher oriTeacher = optionalTeacher.get();
            oriTeacher.setFirstName(te.getFirstName());
            oriTeacher.setLastName(te.getLastName());
            oriTeacher.setGender(te.getGender());
            oriTeacher.setDob(te.getDob());

            if (StringUtils.hasLength(te.getUsername())) {
                oriTeacher.setUsername(te.getUsername());
            }
            if (StringUtils.hasLength(te.getEmail())) {
                oriTeacher.setEmail(te.getEmail());
            }
            if (StringUtils.hasLength(te.getPassword())) {
                oriTeacher.setPassword(encoder.encode(te.getPassword()));
            }
            repo.save(oriTeacher);
        }
        return "redirect:/teacher/list";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String delete(@PathVariable("id") Long id, Model model, HttpSession session) {
        repo.deleteById(id);
        return "redirect:/teacher/list";
    }

}