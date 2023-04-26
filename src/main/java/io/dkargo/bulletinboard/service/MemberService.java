package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.MemberDTO;

public interface MemberService {
    void saveMember(MemberDTO memberDTO);
}
