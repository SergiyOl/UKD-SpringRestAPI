package com.springrest.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springrest.rest.model.RequestStudentDTO;
import com.springrest.rest.model.ResponceStudentDTO;
import com.springrest.rest.model.Student;
import com.springrest.rest.service.StudentServiceImpl;

@RestController
public class HomeRestController {
    private StudentServiceImpl studentService = null;

    public HomeRestController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> listStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/students/")
    public Optional<Student> getStudentById(@RequestParam(required = true) Long id) {
        return studentService.getStudentById(id);
    }

    @PostMapping("/students")
    public ResponceStudentDTO addStudent(@RequestBody RequestStudentDTO requestDTO) {
        Optional<Student> responce = studentService.addStudent(requestDTO.getId(), requestDTO.getName(),
                requestDTO.getAge());
        if (responce != null)
            return (new ResponceStudentDTO(requestDTO.getId(), requestDTO.getName(), requestDTO.getAge()));
        else
            return null;
    }

    @PutMapping("/students")
    public ResponceStudentDTO updateStudent(@RequestBody RequestStudentDTO requestDTO) {
        Optional<Student> responce = studentService.updateStudent(requestDTO.getId(), requestDTO.getName(),
                requestDTO.getAge());
        if (responce != null)
            return (new ResponceStudentDTO(requestDTO.getId(), requestDTO.getName(), requestDTO.getAge()));
        else
            return null;
    }

    @DeleteMapping("/students/")
    public Optional<Student> deleteStudentById(@RequestParam(required = true) Long id) {
        return studentService.deleteStudentById(id);
    }
}