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

    public Optional<Student> addStudent(Long id, String name, int age) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return Optional.empty();
            }
        }
        students.add(new Student(id, name, age));
        return getById(id);
    }

    public Optional<Student> updateById(Long id, String name, int age) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                student.setName(name);
                student.setAge(age);
                return getById(id);
            }
        }
        return Optional.empty();
    }

    public Optional<Student> deleteById(Long id) {
        Student toDelete = null;
        for (Student student : students) {
            if (student.getId().equals(id)) {
                toDelete = student;
            }
        }
        if (toDelete != null) {
            students.remove(toDelete);
        }
        return Optional.empty();
    }

}
