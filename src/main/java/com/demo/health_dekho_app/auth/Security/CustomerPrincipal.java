package com.demo.health_dekho_app.auth.Security;

import com.demo.health_dekho_app.entity.CustomerEntity;
import com.demo.health_dekho_app.entity.enums.CustomerStatus;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomerPrincipal implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;
    private final CustomerStatus status;

    public CustomerPrincipal(CustomerEntity customer) {
        this.id = customer.getId();
        this.email = customer.getEmail();
        this.password = customer.getPasswordHash();
        this.status = customer.getStatus();
    }

    public Long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_CUSTOMER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == CustomerStatus.ACTIVE;
    }
}