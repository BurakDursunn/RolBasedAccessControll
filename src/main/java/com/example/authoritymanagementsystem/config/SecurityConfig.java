package com.example.authoritymanagementsystem.config;

import com.example.authoritymanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserService userService;

    // HTTP güvenlik yapılandırması
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()  // CSRF korumasını devre dışı bırak (API kullanıyorsanız)
                .authorizeHttpRequests()  // authorizeRequests() yerine authorizeHttpRequests() kullanın
                .requestMatchers("/api/auth/**").permitAll()  // Giriş ve kayıt API'leri herkese açık
                .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")  // Kullanıcı ve admin rolleri için
                .requestMatchers("/api/admin/**").hasRole("ADMIN")  // Yalnızca admin rolü
                .anyRequest().authenticated()  // Diğer her şey için kimlik doğrulama gerekli
                .and()
                .httpBasic();  // Basic Authentication
    }

    // UserDetailsService yapılandırması
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Şifre encoder yapılandırması
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // DaoAuthenticationProvider yapılandırması
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
