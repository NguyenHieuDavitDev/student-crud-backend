package com.example.student_crud.service.impl;

import com.example.student_crud.dto.StudentCreateRequest;
import com.example.student_crud.dto.StudentResponse;
import com.example.student_crud.dto.StudentUpdateRequest;
import com.example.student_crud.entity.Student;
import com.example.student_crud.repository.StudentRepository;
import com.example.student_crud.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    private String saveImage(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            return null;
        }

        //  Chỉ cho phép image
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IOException("Only image files are allowed");
        }

        // Làm sạch tên file
        String originalFilename = Paths.get(file.getOriginalFilename()).getFileName().toString();
        String extension = "";

        int dotIndex = originalFilename.lastIndexOf(".");
        if (dotIndex > 0) {
            extension = originalFilename.substring(dotIndex);
        }

        // Tạo tên file mới
        String fileName = UUID.randomUUID() + extension;

        //Tạo thư mục upload (relative path)
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        // Ghi file
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }


    private StudentResponse mapToResponse(Student s) {
        return new StudentResponse(
                s.getId(),
                s.getName(),
                s.getEmail(),
                s.getImageUrl(),
                s.isDeleted()
        );
    }

    @Override
    public StudentResponse create(StudentCreateRequest request, MultipartFile image) throws IOException {
        Student s = new Student();
        s.setName(request.getName());
        s.setEmail(request.getEmail());
        s.setImageUrl(saveImage(image));

        return mapToResponse(repository.save(s));
    }

    @Override
    public List<StudentResponse> findAll() {
        return repository.findByDeletedFalse()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public StudentResponse update(Long id, StudentUpdateRequest request, MultipartFile image) throws IOException {
        Student s = repository.findById(id).orElseThrow();

        s.setName(request.getName());
        s.setEmail(request.getEmail());

        if (image != null && !image.isEmpty()) {
            s.setImageUrl(saveImage(image));
        }

        return mapToResponse(repository.save(s));
    }

    @Override
    public void softDelete(Long id) {
        Student s = repository.findById(id).orElseThrow();
        s.setDeleted(true);
        repository.save(s);
    }
}
