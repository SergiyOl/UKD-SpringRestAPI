package com.springrest.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.springrest.rest.service.StudentServiceImpl;

@Controller
public class HomeController {

    @Autowired
    public HomeController(StudentServiceImpl studentService) {
    }

    @GetMapping("/home")
    public String listStudents() {
        return "home";
    }
}
