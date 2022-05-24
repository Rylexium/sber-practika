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
    private final String address;
    private final boolean enabled;
    private final String bankNumber;
    private final Long balanceBank;
    private final Long mainCardNumber;

    public JwtUser(String phone, String password,
                   String name, String family, String patronymic,
                   String salt1, String salt2,
                   String email,
                   String address, boolean isEnabled,
                   String bankNumber,
                   Long balanceBank,
                   Long mainCardNumber) {
        this.username = phone.toString();
        this.name = name;
        this.family = family;
        this.salt1 = salt1;
        this.salt2 = salt2;
        this.patronymic = patronymic;
        this.password = password;
        this.email = email;
        this.address = address;
        this.enabled = isEnabled;
        this.bankNumber = bankNumber;
        this.balanceBank = balanceBank;
        this.mainCardNumber = mainCardNumber;
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
