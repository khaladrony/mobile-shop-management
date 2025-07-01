package com.rony.erpsoft.configuration.security;

import com.rony.erpsoft.user_auth.service.SessionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SessionValidationFilter extends OncePerRequestFilter {

    @Autowired
    SessionService sService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();

        // If session is invalid and path is not already login page
        /*if (sService.getUserId() == null && !uri.startsWith("/auth/")) {
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }*/

        filterChain.doFilter(request, response);
    }
}
