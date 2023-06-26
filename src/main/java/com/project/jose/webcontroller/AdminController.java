package com.project.jose.webcontroller;


import com.project.jose.account.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    @GetMapping("/list")
    public String list(Model model, HttpSession session) {
        model.addAttribute("admins", adminRepo.findAll());
        if (session.getAttribute("admin") == null) {
            model.addAttribute("admin", new Admin());
            model.addAttribute("btnAddOrModifyLabel", "Add");
        } else {
            model.addAttribute("admin", session.getAttribute("admin"));
            model.addAttribute("btnAddOrModifyLabel", "Modify");
        }
        return "admin/list";
    }

    @GetMapping("/create_student")
    public String createStudent(Model model, HttpSession session) {
        model.addAttribute("students", studentRepo.findAll());
        if (session.getAttribute("student") == null) {
            model.addAttribute("student", new Student());
            model.addAttribute("btnAddOrModifyLabel", "Add");
        } else {
            model.addAttribute("admin", session.getAttribute("admin"));
            model.addAttribute("btnAddOrModifyLabel", "Modify");
        }
        return "admin/list";
    }


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model, HttpSession session) {
        adminRepo.deleteById(id);
        return "redirect:/admin";
    }
}
