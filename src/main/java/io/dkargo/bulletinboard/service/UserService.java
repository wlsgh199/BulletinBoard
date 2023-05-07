package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.user.ReqAddUserDTO;

public interface UserService {
    //유저 추가
    void addMember(ReqAddUserDTO reqAddUserDTO);
}
