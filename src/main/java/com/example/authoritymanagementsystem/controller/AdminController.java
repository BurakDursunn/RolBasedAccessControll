package com.example.authoritymanagementsystem.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    // Admin yönetim ekranı
    @GetMapping("/manage")
    public String adminDashboard() {
        return "Admin'e özel yönetim ekranı!";
    }
}

