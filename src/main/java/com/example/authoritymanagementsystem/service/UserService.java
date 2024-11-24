package com.example.authoritymanagementsystem.service;

import com.example.authoritymanagementsystem.model.Role;
import com.example.authoritymanagementsystem.model.User;
import com.example.authoritymanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;  // BCryptPasswordEncoder'ı burada kullanıyoruz

    @Autowired
    private RoleService roleService;

    // Yeni kullanıcı kaydetme
    @Transactional
    public User registerNewUser(User user) {
        // Şifreyi şifrele
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Varsayılan rol ekleme (örneğin USER rolü)
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByName("USER"));
        user.setRoles(roles);

        return userRepository.save(user);
    }

    // Kullanıcı adıyla arama
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
