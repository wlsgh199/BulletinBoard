package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.ReqUserDTO;
import io.dkargo.bulletinboard.entity.User;

public interface UserService {
    void saveMember(ReqUserDTO reqUserDTO);
}
