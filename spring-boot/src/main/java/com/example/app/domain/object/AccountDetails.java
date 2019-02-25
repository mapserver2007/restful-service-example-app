package com.example.app.domain.object;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AccountDetails implements UserDetails {

    private Account account;
    private Collection<GrantedAuthority> authorities;

    public AccountDetails(Account account, Collection<GrantedAuthority> authorities) {
        this.account = account;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return account.getLoginId();
    }

    @Override
    public String getPassword() {
        return account.getPassword();
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
        return true;
    }
}
