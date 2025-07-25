package com.rony.erpsoft.configuration.security;

import com.rony.erpsoft.user_auth.service.SessionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class OrganizationContextFilter extends OncePerRequestFilter {

    private final SessionService sessionService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // Assume your sessionService can resolve current org ID
            Long organizationId = sessionService.getOrganizationId(); 
            SessionContext.setOrganizationId(organizationId);

            filterChain.doFilter(request, response);
        } finally {
            SessionContext.clear(); // prevent memory leaks
        }
    }
}
