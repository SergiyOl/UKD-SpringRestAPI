package com.springrest.rest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.springrest.rest.model.Student;
import com.springrest.rest.repository.StudentRepository;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.getAll();
    }

    public Optional<Student> getStudentById(Long id) {
        return studentRepository.getById(id);
    }

    public Optional<Student> addStudent(Long id, String name, int age) {
        return studentRepository.addStudent(id, name, age);
    }

    public Optional<Student> updateStudent(Long id, String name, int age) {
        return studentRepository.updateById(id, name, age);
    }

    public Optional<Student> deleteStudentById(Long id) {
        return studentRepository.deleteById(id);
    }
}
