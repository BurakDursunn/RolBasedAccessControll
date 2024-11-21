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
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    // Yeni kullanıcı kaydı
    @Transactional
    public User registerNewUser(User user) {
        // Şifreyi şifrele
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Varsayılan "USER" rolünü al
        Role userRole = roleService.getRoleByName("USER");

        if (userRole == null) {
            // Eğer "USER" rolü yoksa, rolü oluştur ve veritabanına kaydet
            userRole = new Role();
            userRole.setName("USER");
            roleService.saveRole(userRole);  // Bu metodun eklenmesi gerektiğini unutma!
        }

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);  // Rolü kullanıcıya ekle
        user.setRoles(roles);

        return userRepository.save(user);  // Kullanıcıyı kaydet
    }

    // Kullanıcı adıyla kullanıcı arama
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
