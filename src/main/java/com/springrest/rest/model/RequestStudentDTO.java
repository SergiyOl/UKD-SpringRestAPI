package com.springrest.rest.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestStudentDTO {
    private Long id;
    private String name;
    private int age;
}
