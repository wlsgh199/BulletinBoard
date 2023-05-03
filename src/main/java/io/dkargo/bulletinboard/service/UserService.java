package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.user.ReqAddUserDTO;

public interface UserService {
    void saveMember(ReqAddUserDTO reqAddUserDTO);
}
