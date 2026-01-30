package com.example.student_crud.entity;

import jakarta.persistence.*;
import lombok.Data;


    @Entity
    @Table(name = "students")
    @Data
    public class Student {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name;
        private String email;

        private String imageUrl;

        private boolean deleted = false;
    }


