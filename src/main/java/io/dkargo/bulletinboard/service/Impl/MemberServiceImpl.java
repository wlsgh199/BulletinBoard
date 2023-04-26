package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.MemberDTO;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.repository.MemberRepository;
import io.dkargo.bulletinboard.service.MemberService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void addMember(MemberDTO memberDTO) {
        Member member = new Member(memberDTO);
        member.setCreateTime(LocalDateTime.now());
        memberRepository.save(member);
    }
}
