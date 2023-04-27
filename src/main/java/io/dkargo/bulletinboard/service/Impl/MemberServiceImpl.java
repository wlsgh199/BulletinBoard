package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.dto.request.ReqMemberDTO;
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

    /**
     * 회원 추가
     * @param reqMemberDTO 회원 정보
     */
    @Override
    public void saveMember(ReqMemberDTO reqMemberDTO) {
        Member member = new Member(reqMemberDTO);
        member.setCreateTime(LocalDateTime.now());
        memberRepository.save(member);
    }
}
