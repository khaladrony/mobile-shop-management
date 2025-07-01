package com.rony.erpsoft.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   CustomAccessDeniedHandler deniedHandler,
                                                   SessionValidationFilter sessionValidationFilter,
                                                   TokenValidationFilter tokenValidationFilter
    ) throws Exception {
        http
//                .addFilterBefore(sessionValidationFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(tokenValidationFilter, SessionValidationFilter.class) // order matters!
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/**",
                                "/user_auth/**",
                                "/accounts/**",
                                "/inventory/**",
                                "/application_common/**",
                                "/dashboard/**",
                                "/resources/**",
                                "/view/**",
                                "/res/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler(deniedHandler)
                )
                .csrf(csrf -> csrf.disable()); // New syntax for disabling CSRF

        return http.build();
    }
}
