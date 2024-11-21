package com.example.authoritymanagementsystem.controller;


import com.example.authoritymanagementsystem.model.User;
import com.example.authoritymanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // Kullanıcı kaydı
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Yeni kullanıcı kaydet
        userService.registerNewUser(user);
        return ResponseEntity.ok("Kullanıcı başarıyla kaydedildi.");
    }
}

