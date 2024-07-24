package ba.sum.fsre.ednevnik1.models;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.stream.Collectors;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private user user;

    public UserDetails(user user) {
        this.user = user;
    }


    @Override
    public String getPassword() {
        return user.getLozinka();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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

    public String getFullName() {
        return user.getIme() + " " + user.getPrezime();
    }

    public user getUser() {
        return user;
    }

    public void setUser(user user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }
}