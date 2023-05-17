package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.member.ReqCreateMemberDTO;
import io.dkargo.bulletinboard.dto.request.member.ReqUpdateMemberDTO;
import io.dkargo.bulletinboard.dto.response.member.ResMemberTokenDTO;
import io.dkargo.bulletinboard.dto.response.member.ResCreateMemberDTO;
import io.dkargo.bulletinboard.dto.response.member.ResFindMemberDTO;
import io.dkargo.bulletinboard.dto.response.member.ResUpdateMemberDTO;
import io.dkargo.bulletinboard.entity.Member;

public interface MemberService {
    //유저 추가
    ResCreateMemberDTO createUser(ReqCreateMemberDTO reqCreateMemberDTO);

    void deleteUserById(long memberId);

    ResMemberTokenDTO login(String email, String password);

    ResMemberTokenDTO reissue(String accessToken, String refreshToken);

    void logout(String accessToken, String refreshToken);

    ResFindMemberDTO findMember(Member member);

    void grantAdmin(long memberId);

    ResUpdateMemberDTO updateMember(ReqUpdateMemberDTO reqUpdateMemberDTO, Member member);
}
