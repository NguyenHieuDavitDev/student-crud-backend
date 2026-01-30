package com.example.student_crud.dto;

import lombok.Data;

@Data
public class StudentCreateRequest {
    private String name;
    private String email;
}
