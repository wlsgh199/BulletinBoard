package io.dkargo.bulletinboard.jwt;

import io.dkargo.bulletinboard.entity.Member;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

//Security ContextHolder
@Getter
public class MemberDetails extends User {

    //member id
    private final MemberDetailsDTO memberDetailsDTO;

    public MemberDetails(Member member, MemberDetailsDTO memberDetailsDTO) {
        super(member.getEmail(), member.getPassword(), List.of(new SimpleGrantedAuthority(member.getRole().getValue())));
        this.memberDetailsDTO = memberDetailsDTO;
    }
}
