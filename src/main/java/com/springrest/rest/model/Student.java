package com.springrest.rest.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Long id;

    @NotBlank(message = "Ім'я не може бути порожнім")
    @Size(min = 2, max = 50, message = "Ім'я має бути від 2 до 50 символів")
    private String name;

    @NotNull(message = "Вік не може бути null")
    @Min(value = 1, message = "Вік має бути більше 0")
    private int age;
}