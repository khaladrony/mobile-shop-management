package com.rony.erpsoft.configuration.security;

import com.rony.erpsoft.user_auth.service.AuthService;
import com.rony.erpsoft.user_auth.service.SessionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;

@Component
public class TokenValidationFilter extends OncePerRequestFilter {

    @Autowired
    SessionService sService;
    @Autowired
    HttpSession session;
    @Autowired
    AuthService authService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("x-aip-token");
        String uri = request.getRequestURI().toLowerCase();

        boolean isViewGetReq = uri.contains("/view") || uri.contains(".png") || uri.contains(".jpg") || uri.contains(".jpeg");

        /*if (!isViewGetReq && sService.getUserId() != null && !sService.getToken().equals(token)) {
            authService.endedLoginSession(sService.getUser());
            session.invalidate();
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid Token");
            return;
        }*/

        filterChain.doFilter(request, response);
    }
}
