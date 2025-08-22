package com.rony.erpsoft.configuration.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler successHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // public endpoints
                        .requestMatchers("/auth/**", "/resources/**", "/view/**", "/res/**").permitAll()
                        // protected endpoints
                        .requestMatchers("/user_auth/**", "/accounts/**", "/inventory/**", "/sales/**",
                                "/application_common/**", "/dashboard/**").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/auth/login")              // GET login page
                        .loginProcessingUrl("/auth/login")     // POST login submission
                        .usernameParameter("lanId")            // map to your form
                        .passwordParameter("usrpkeycnv")       // encrypted password hidden field
                        .successHandler(successHandler)
                        .failureUrl("/auth/login?error")       // failure redirect
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .invalidSessionUrl("/auth/login")
                        .maximumSessions(1)
                );

        return http.build();
    }

    // Authentication provider that decrypts AES password and validates user
    @Bean
    public AuthenticationManager authManager(HttpSecurity http, CustomAuthenticationProvider customAuthProvider) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(customAuthProvider)
                .build();
    }
}
