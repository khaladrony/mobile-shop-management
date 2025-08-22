package com.rony.erpsoft.configuration.security;

import com.rony.erpsoft.user_auth.model.UserInfo;
import com.rony.erpsoft.user_auth.repo.AuthRepo;
import com.rony.erpsoft.user_auth.repo.OrganizationRepo;
import com.rony.erpsoft.user_auth.service.AuthService;
import com.rony.erpsoft.utils.AppUtil;
import com.rony.erpsoft.utils.KEY;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@AllArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final AuthRepo authRepo;
    private final AppUtil appUtil;
    private final OrganizationRepo organizationRepo;
    private final AuthService authService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();

        // Fetch user from DB
        UserInfo user = authRepo.findUserByLanId(username);
        if (user != null) {
            // Business rule 1: Single session check
            if (!user.getRole_name().equalsIgnoreCase("SuperAdmin") &&
                    !user.getRole_name().equalsIgnoreCase("Admin") &&
                    authRepo.getLoginSessionLog(user.getUser_id()) > 0) {

                // Invalidate session
                request.getSession().invalidate();

                // Redirect back with error
                response.sendRedirect("/auth/login?errorMessage=" + URLEncoder.encode(
                        "Please, logout from all other devices at first!", StandardCharsets.UTF_8));
                return;
            }

            // Business rule 2: License check
            /*if (!authService.licenseKeyCheck()) {
                request.getSession().invalidate();
                response.sendRedirect("/auth/login?errorMessage=" + URLEncoder.encode(
                        "Please contact your vendor. You have a licence problem!!!", StandardCharsets.UTF_8));
                return;
            }*/

            HttpSession session = request.getSession();
            session.setAttribute(KEY.USER, user);
            session.setAttribute(KEY.ORGANIZATION, organizationRepo.findById(user.getOrganization_id()));
            session.setMaxInactiveInterval(appUtil.getSessionTimeout());
        }

        // Redirect to success page
        response.sendRedirect(request.getContextPath() + "/auth/success");
    }
}
