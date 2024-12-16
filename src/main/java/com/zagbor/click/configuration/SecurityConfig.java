package com.zagbor.click.configuration;

import com.zagbor.click.service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Включаем управление сессиями
                ) // Отключение CSRF-защиты
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/error").permitAll()  // Разрешаем доступ к /error
                        .requestMatchers("/account/**")
                        .authenticated()// Доступ к /account/** только для пользователей с ролью USER// Доступ к /account/** только для авторизованных
                        .anyRequest().permitAll()  // Доступ ко всем остальным URL
                )
                .formLogin(form -> form
                        .loginPage("/login")  // Страница входа
                        .permitAll()  // Доступ к странице входа всем
                        .defaultSuccessUrl("/account", true)  // Перенаправление после успешного входа
                )
                .logout(logout -> logout
                        .permitAll()  // Доступ к функционалу logout для всех
                        .logoutUrl("/logout")  // URL для выхода
                        .logoutSuccessUrl("/login")  // Перенаправление после выхода
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authManagerBuilder = httpSecurity.getSharedObject(
                AuthenticationManagerBuilder.class);
        authManagerBuilder.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
        return authManagerBuilder.build();
    }

}


