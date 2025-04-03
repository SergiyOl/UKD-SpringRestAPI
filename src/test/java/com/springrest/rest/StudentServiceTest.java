package com.springrest.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.springrest.rest.model.Student;
import com.springrest.rest.repository.StudentRepository;
import com.springrest.rest.service.StudentServiceImpl;

@SpringBootTest
@RunWith(MockitoExtension.class)
public class StudentServiceTest {

        @Mock
        private StudentRepository studentRepository;

        @InjectMocks
        private StudentServiceImpl studentService;

        @Test
        void testGetAllStudents() throws Exception {
                final List<Student> students = new ArrayList<>();
                students.add(new Student((long) 0, "Serhii", 16));
                students.add(new Student((long) 1, "John", 19));
                students.add(new Student((long) 2, "Kate", 17));

                when(studentRepository.getAll())
                                .thenReturn(students);

                List<Student> savedStudent = studentService.getAllStudents();

                assertNotNull(savedStudent);
                assertEquals(savedStudent, students);
                assertEquals(savedStudent.getFirst(), students.getFirst());
                verify(studentRepository, times(1)).getAll();
        }

        @Test
        void testAddStudent() throws Exception {
                when(studentRepository.addStudent(3L, "Mock Test Student", 23))
                                .thenReturn(Optional.of(new Student(3L, "Mock Test Student", 23)));

                Optional<Student> savedStudent = studentService.addStudent(3L, "Mock Test Student", 23);
                assertNotNull(savedStudent.get().getId());
                assertEquals("Mock Test Student", savedStudent.get().getName());
                assertEquals(23, savedStudent.get().getAge());
                verify(studentRepository, times(1)).addStudent(any(Long.class), any(String.class), any(int.class));
        }

        @Test
        void testGetStudentById() throws Exception {
                final List<Student> students = new ArrayList<>();
                students.add(new Student((long) 0, "Serhii", 16));
                students.add(new Student((long) 1, "John", 19));
                students.add(new Student((long) 2, "Kate", 17));

                when(studentRepository.getById(0L))
                                .thenReturn(Optional.of(students.get(0)));
                when(studentRepository.getById(1L))
                                .thenReturn(Optional.of(students.get(1)));
                when(studentRepository.getById(2L))
                                .thenReturn(Optional.of(students.get(2)));

                Optional<Student> savedStudent = studentService.getStudentById(2L);

                assertNotNull(savedStudent.get().getId());
                assertEquals("Kate", savedStudent.get().getName());
                assertEquals(17, savedStudent.get().getAge());
                verify(studentRepository, times(1)).getById(any(Long.class));
        }

        @Test
        void testUpdateStudent() throws Exception {
                when(studentRepository.updateById(2L, "Mock Test Student", 23))
                                .thenReturn(Optional.of(new Student(2L, "Mock Test Student", 23)));

                Optional<Student> savedStudent = studentService.updateStudent(2L, "Mock Test Student", 23);
                assertNotNull(savedStudent.get().getId());
                assertEquals("Mock Test Student", savedStudent.get().getName());
                assertEquals(23, savedStudent.get().getAge());
                verify(studentRepository, times(1)).updateById(any(Long.class), any(String.class), any(int.class));
        }

        @Test
        void testDeleteStudentById() throws Exception {
                when(studentRepository.deleteById(2L))
                                .thenReturn(null);

                Optional<Student> savedStudent = studentService.deleteStudentById(2L);
                assertNull(savedStudent);
                verify(studentRepository, times(1)).deleteById(any(Long.class));
        }
}
