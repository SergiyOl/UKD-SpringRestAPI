package com.springrest.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.springrest.rest.model.Student;
import com.springrest.rest.repository.StudentRepository;
import com.springrest.rest.service.StudentServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void testAddStudent() throws Exception {
        when(studentRepository.addStudent(any(Long.class), any(String.class), any(int.class)))
                .thenReturn(Optional.of(new Student(Long.valueOf(3), "Mock Test Student", 23)));

        Optional<Student> savedStudent = studentService.addStudent(Long.valueOf(3), "Mock Test Student", 23);

        assertNotNull(savedStudent.get().getId());
        assertEquals("Mock Test Student", savedStudent.get().getName());
        assertEquals(23, savedStudent.get().getAge());
        verify(studentRepository, times(1)).addStudent(any(Long.class), any(String.class), any(int.class));
    }
}
