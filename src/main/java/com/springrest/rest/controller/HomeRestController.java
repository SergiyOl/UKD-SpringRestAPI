package com.springrest.rest.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.rest.model.Student;
import com.springrest.rest.service.StudentService;

@RestController
public class HomeRestController {
    private StudentService studentService = null;

    public HomeRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> listStudents() {
        return studentService.getAllStudents();
    }
}
