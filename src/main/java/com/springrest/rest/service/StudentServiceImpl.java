package com.springrest.rest.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.springrest.rest.model.Student;
import com.springrest.rest.repository.StudentRepository;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public List<Student> getAllStudents() {
        logger.info("Started looking for all students");
        return studentRepository.getAll();
    }

    public Optional<Student> getStudentById(Long id) {
        logger.info("Started looking for student with id " + id);
        return studentRepository.getById(id);
    }

    public Optional<Student> addStudent(Long id, String name, int age) {
        logger.info("Started adding student " + id + " " + name + " " + age);
        return studentRepository.addStudent(id, name, age);
    }

    public Optional<Student> updateStudent(Long id, String name, int age) {
        logger.info("Started updating student with id " + id + " to " + id + " " + name + " " + age);
        return studentRepository.updateById(id, name, age);
    }

    public Optional<Student> deleteStudentById(Long id) {
        logger.info("Started deleting student with id " + id);
        return studentRepository.deleteById(id);
    }
}
