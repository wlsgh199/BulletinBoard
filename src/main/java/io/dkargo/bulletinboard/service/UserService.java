package io.dkargo.bulletinboard.service;

import io.dkargo.bulletinboard.dto.request.user.ReqAddUserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    //유저 추가
    void addUser(ReqAddUserDTO reqAddUserDTO);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
