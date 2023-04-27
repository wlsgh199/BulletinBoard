package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.ReqMemberDTO;

public interface MemberService {
    void saveMember(ReqMemberDTO reqMemberDTO);
}
