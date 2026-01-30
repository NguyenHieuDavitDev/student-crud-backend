package com.example.student_crud.controller;

import com.example.student_crud.dto.StudentCreateRequest;
import com.example.student_crud.dto.StudentResponse;
import com.example.student_crud.dto.StudentUpdateRequest;
import com.example.student_crud.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public StudentResponse create(
            @ModelAttribute StudentCreateRequest request,
            @RequestPart(required = false) MultipartFile image
    ) throws IOException {
        return service.create(request, image);
    }

    @GetMapping
    public List<StudentResponse> getAll() {
        return service.findAll();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public StudentResponse update(
            @PathVariable Long id,
            @ModelAttribute StudentUpdateRequest request,
            @RequestPart(required = false) MultipartFile image
    ) throws IOException {
        return service.update(id, request, image);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.softDelete(id);
    }
}
