package com.sber.practika.security.jwt;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
public class JwtUser implements UserDetails {
    private final String username;
    private final String name;
    private final String family;
    private final String salt1;
    private final String salt2;
    private final String patronymic;
    private final String password;
    private final String email;
    private final String phone;
    private final boolean enabled;

    public JwtUser(String username, String password,
                   String name, String family, String patronymic,
                   String salt1, String salt2,
                   String email, String phone,
                   boolean isEnabled) {
        this.username = username;
        this.name = name;
        this.family = family;
        this.salt1 = salt1;
        this.salt2 = salt2;
        this.patronymic = patronymic;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.enabled = isEnabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
}
