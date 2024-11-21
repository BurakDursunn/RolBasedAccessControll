package com.example.authoritymanagementsystem.service;

import com.example.authoritymanagementsystem.model.Role;
import com.example.authoritymanagementsystem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // Rol ismine göre rol al
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }
}
