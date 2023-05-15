package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.user.ReqCreateMemberDTO;
import io.dkargo.bulletinboard.dto.request.user.MemberTokenDTO;

public interface MemberService {
    //유저 추가
    void createUser(ReqCreateMemberDTO reqCreateMemberDTO);
    void deleteUser(String email);
    MemberTokenDTO login(String email, String password);
    MemberTokenDTO reissue(String accessToken, String refreshToken);
}
