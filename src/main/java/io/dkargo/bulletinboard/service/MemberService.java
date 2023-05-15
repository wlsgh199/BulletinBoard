package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.member.ReqCreateMemberDTO;
import io.dkargo.bulletinboard.dto.request.member.MemberTokenDTO;
import io.dkargo.bulletinboard.dto.response.member.ResCreateMemberDTO;
import io.dkargo.bulletinboard.dto.response.member.ResFindMemberDTO;
import io.dkargo.bulletinboard.entity.Member;

public interface MemberService {
    //유저 추가
    ResCreateMemberDTO createUser(ReqCreateMemberDTO reqCreateMemberDTO);
    void deleteUserById(long memberId);
    MemberTokenDTO login(String email, String password);
    MemberTokenDTO reissue(String accessToken, String refreshToken);

    ResFindMemberDTO findMember(Member member);
}
