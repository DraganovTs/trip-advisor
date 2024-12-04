package com.trip.advisor.user.service.model.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private UUID id;
    private String email;
    private String password;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;


    public CustomUserDetails(UUID id, String email, String password, String username,
                             Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
        this.password = password;
        this.id = id;
        this.username = username;
        this.email = email;
        this.enabled = true;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
    }

    public static CustomUserDetails build(User user) {
        List<GrantedAuthority> authorities = getAuthorities(user);

        return new CustomUserDetails(user.getId(), user.getEmail(), user.getPassword(), user.getUsername(), authorities);
    }

    public static List<GrantedAuthority> getAuthorities(User user) {

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .flatMap(role -> role.getPrivileges().stream()
                        .map(privilege -> new SimpleGrantedAuthority(privilege.getName()))).collect(Collectors.toList());

        return authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return isEnabled();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomUserDetails that = (CustomUserDetails) o;
        return Objects.equals(id, that.id);
    }

}
