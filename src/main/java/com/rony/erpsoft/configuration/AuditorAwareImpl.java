package com.rony.erpsoft.configuration;

import com.rony.erpsoft.user_auth.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AuditorAwareImpl implements AuditorAware<Long> {

    private final SessionService sessionService;

    @Override
    public Optional<Long> getCurrentAuditor() {
        return Optional.ofNullable(sessionService.getUserId());
    }
}
