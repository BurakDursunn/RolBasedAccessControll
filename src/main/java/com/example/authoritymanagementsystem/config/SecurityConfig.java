package com.example.authoritymanagementsystem.config;

import com.example.authoritymanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig   {

    @Autowired
    private UserService userService;

    //@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()  // Giriş ve kayıt API'leri herkese açık
                .antMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")  // Kullanıcı ve admin rolleri için
                .antMatchers("/api/admin/**").hasRole("ADMIN")  // Yalnızca admin rolü
                .anyRequest().authenticated()  // Diğer her şey için kimlik doğrulama gerekli
                .and()
                .httpBasic();  // HTTP Basic Authentication kullanıyoruz
    }

    //@Override
    @Bean
    public UserDetailsService userDetailsService() {
        // Kullanıcıyı username ile buluyoruz
        return username -> (UserDetails) userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // BCryptPasswordEncoder kullanarak şifreleme yapıyoruz
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        // Kullanıcı doğrulama sağlayıcısını oluşturuyoruz
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
