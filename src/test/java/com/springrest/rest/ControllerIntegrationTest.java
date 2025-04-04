package com.springrest.rest;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertNull;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// import com.springrest.rest.controller.HomeRestController;
import com.springrest.rest.model.Student;
// import com.springrest.rest.service.StudentServiceImpl;
// import com.springrest.rest.repository.StudentRepository;

import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllStudents() throws Exception {
        final List<Student> students = new ArrayList<>();
        students.add(new Student((long) 0, "Serhii", 16));
        students.add(new Student((long) 1, "John", 19));
        students.add(new Student((long) 2, "Kate", 17));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(students)));
    }

    @Test
    void testGetStudentById() throws Exception {
        Student student = new Student((long) 2, "Kate", 17);

        mockMvc.perform(get("/students/?id=2"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .json(objectMapper.writeValueAsString(Optional.of(student))));
    }

    @Test
    void testAddStudent() throws Exception {
        Student student = new Student(3L, "Mock Test Student", 23);

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Mock Test Student"))
                .andExpect(jsonPath("$.age").value(23));

        // Cleanup
        mockMvc.perform(delete("/students/?id=3"));
    }

    @Test
    void testUpdateStudent() throws Exception {
        Student student = new Student(1L, "Mock Test Student", 23);

        mockMvc.perform(put("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mock Test Student"))
                .andExpect(jsonPath("$.age").value(23));

        // Cleanup
        student = new Student(1L, "John", 19);
        mockMvc.perform(put("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));
    }

    @Test
    void testDeleteStudentById() throws Exception {
        mockMvc.perform(delete("/students/?id=0"))
                .andExpect(status().isNoContent());

        // Cleanup
        Student student = new Student((long) 0, "Serhii", 16);

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));
    }
}
