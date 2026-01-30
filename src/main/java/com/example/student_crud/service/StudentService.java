package com.example.student_crud.service;

import com.example.student_crud.dto.StudentCreateRequest;
import com.example.student_crud.dto.StudentResponse;
import com.example.student_crud.dto.StudentUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService {

    StudentResponse create(StudentCreateRequest request, MultipartFile image) throws IOException;

    List<StudentResponse> findAll();

    StudentResponse update(Long id, StudentUpdateRequest request, MultipartFile image) throws IOException;

    void softDelete(Long id);
}
