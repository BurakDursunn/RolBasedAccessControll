package com.example.authoritymanagementsystem.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    // Kullanıcı dashboard
    @GetMapping("/dashboard")
    public String userDashboard() {
        return "Kullanıcıya özel bir dashboard!";
    }
}
