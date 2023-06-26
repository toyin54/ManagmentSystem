package com.project.jose.webcontroller;

import com.project.jose.account.*;
import com.project.jose.course.Course;
import com.project.jose.course.CourseRepository;
import com.project.jose.course.CourseSearch;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String list(Model model, HttpSession session) {
        model.addAttribute("courses", courseRepository.findAll());
        if (session.getAttribute("course") == null) {
            model.addAttribute("course", new Course());
            model.addAttribute("btnAddOrModifyLabel", "Add");
        } else {
            model.addAttribute("course", session.getAttribute("course"));
            model.addAttribute("btnAddOrModifyLabel", "Modify");
        }
        return "course/list_courses";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String createCourseForm(Model model) {
        Course course = new Course();
        model.addAttribute("course", course);
        return "course/create_course";
    }

    @PostMapping("/new")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String createCourse(@ModelAttribute("course") Course course) {
        courseRepository.save(course);
        return "redirect:/course/list";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String get(@PathVariable("id") Long id, Model model, HttpSession session) {
        Course course = courseRepository.findById(id).get();
        model.addAttribute("course", course);
        return "course/edit_course";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public String editCourse(@PathVariable("id") Long id, @ModelAttribute("course") Course course) {
        course.setId(id);
        courseRepository.save(course);
        return "redirect:/course/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model, HttpSession session) {
        courseRepository.deleteById(id);
        return "redirect:/course/list";
    }

    //    @PostMapping
    public String validatedSave(@ModelAttribute Course course) {
        if (course.getId() == 0)
            courseRepository.save(course);
        else {
            var editCourse = courseRepository.findById(course.getId()).get();
            editCourse.setDept(course.getDept());
            editCourse.setNum(course.getNum());
            courseRepository.save(editCourse);
        }
        return "course/edit";
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public String showSearchPage(Model model) {
        CourseSearch courseSearch = new CourseSearch();
        model.addAttribute("courseSearch", courseSearch);
        return "course/search_page";
    }

    @PostMapping("/search_result")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public String searchCourses(@ModelAttribute("courseSearch") CourseSearch courseSearch, Model model) {
        List<Course> searchResults = courseRepository.search(courseSearch.getKeyword());

        model.addAttribute("courses", searchResults);

        return "course/search_result";
    }
}
