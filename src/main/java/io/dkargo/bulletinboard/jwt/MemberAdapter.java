package io.dkargo.bulletinboard.jwt;

import io.dkargo.bulletinboard.entity.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

//Security ContextHolder
@Getter
public class MemberAdapter extends User {

    private final Member member;

    public MemberAdapter(Member member) {
        super(member.getEmail(), member.getPassword(), List.of(new SimpleGrantedAuthority(member.getRole().getValue())));
        this.member = member;
    }
}
