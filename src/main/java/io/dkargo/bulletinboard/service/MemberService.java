package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.ReqMemberDTO;
import io.dkargo.bulletinboard.entity.Member;

public interface MemberService {
    void saveMember(ReqMemberDTO reqMemberDTO);

    Member findMemberById(Long id);
}
