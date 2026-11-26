package com.demo.health_dekho_app.auth.Service;

import com.demo.health_dekho_app.auth.Security.CustomerPrincipal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserService {

    public CustomerPrincipal getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                !(authentication.getPrincipal() instanceof CustomerPrincipal customer)) {

            throw new IllegalStateException(
                "No authenticated customer found"
            );
        }

        return customer;
    }

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }
}
