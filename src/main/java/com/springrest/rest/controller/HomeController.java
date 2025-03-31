package com.springrest.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springrest.rest.service.StudentService;

@Controller
public class HomeController {
    private StudentService studentService;

    @Autowired
    public HomeController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/home")
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "home";
    }
}
