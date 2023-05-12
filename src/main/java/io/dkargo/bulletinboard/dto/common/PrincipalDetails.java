package io.dkargo.bulletinboard.dto.common;

import io.dkargo.bulletinboard.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

//Security ContextHolder
@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().getValue()));
        return roles;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getName();
    }


    //이계정 만료된거니 ?
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //이계정 잠겼니?
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //이계정 비밀번호 1년이지났니 ?
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //이계정이 활성화 된거니?
    @Override
    public boolean isEnabled() {
        return true;
    }
}
