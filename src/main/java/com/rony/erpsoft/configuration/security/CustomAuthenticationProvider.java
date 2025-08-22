package com.rony.erpsoft.configuration.security;

import com.rony.erpsoft.user_auth.model.UserInfo;
import com.rony.erpsoft.user_auth.repo.AuthRepo;
import com.rony.erpsoft.user_auth.service.AuthService;
import com.rony.erpsoft.utils.AppUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AuthRepo authRepo;
    private final AppUtil appUtil;
    private final AuthService authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String encryptedPassword = authentication.getCredentials().toString();

        // decrypt AES password
        String plainPassword = appUtil.retrievePaswd(encryptedPassword);

        // find user in DB
        UserInfo user = authRepo.findUserByLanId(username, appUtil.SHA512(plainPassword));
        if (user == null) {
            throw new BadCredentialsException("Invalid username or password");
        } else {
            authService.createLoginSession(user, encryptedPassword);
        }

        // optional: check concurrent sessions, roles, etc.

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
