package io.dkargo.bulletinboard.jwt;

import io.dkargo.bulletinboard.entity.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

//Security ContextHolder
@Getter
public class MemberAdapter extends User {

    private Member member;

    public MemberAdapter(Member member) {
        super(member.getEmail(), member.getPassword(), List.of(new SimpleGrantedAuthority(member.getRole().getValue())));
        this.member = member;
    }


//    private Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<GrantedAuthority> roles = new HashSet<>();
//        roles.add(new SimpleGrantedAuthority(member.getRole().getValue()));
//        return roles;
//    }

//    @Override
//    public String getPassword() {
//        return member.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return member.getEmail();
//    }
//
//
//    //이계정 만료된거니 ?
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    //이계정 잠겼니?
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    //이계정 비밀번호 1년이지났니 ?
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    //이계정이 활성화 된거니?
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}
