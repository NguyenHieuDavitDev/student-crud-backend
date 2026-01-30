package com.example.student_crud.dto;

import lombok.Data;

@Data
public class StudentUpdateRequest {
    private String name;
    private String email;
}
