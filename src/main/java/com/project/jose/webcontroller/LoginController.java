package com.project.jose.webcontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collection;

@Controller
@Slf4j
public class LoginController {

    @RequestMapping({"/login", "login", "/login.html"})
    public String login() {
        return "login";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping({"/admin", "/admin.html"})
    public String admin(Model model){
        model.addAttribute("user", getUsername());
        model.addAttribute("role", getAuthority());
        return "admin";
    }

    @PreAuthorize("hasAnyAuthority('TEACHER')")
    @RequestMapping({"/teacher", "/teacher.html"})
    public String teacher(Model model){
        model.addAttribute("user", getUsername());
        model.addAttribute("role", getAuthority());
        return "teacher";
    }

    @PreAuthorize("hasAnyAuthority('STUDENT')")
    @RequestMapping({"/student", "/student.html"})
    public String student(Model model){
        model.addAttribute("user", getUsername());
        model.addAttribute("role", getAuthority());
        return "student";
    }

    private String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private String getAuthority(){
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        ArrayList<String> list = new ArrayList<>();
        for(GrantedAuthority authority: authorities){
            list.add(authority.getAuthority());
        }
        log.info("=== authority：" + list);
        return list.toString();
    }
}
