package com.springrest.rest.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.springrest.rest.model.Student;

@Repository
public class StudentRepository {
    public final List<Student> students = new ArrayList<>();

    public StudentRepository() {
        students.add(new Student((long) 0, "Serhii", 16));
        students.add(new Student((long) 1, "John", 19));
        students.add(new Student((long) 2, "Kate", 17));
    }

    public List<Student> getAll() {
        return students;
    }

    public Optional<Student> getById(Long id) {
        return students.stream()
                .filter(student -> student.getId() == (id))
                .findFirst();
    }
}
