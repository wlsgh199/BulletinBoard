package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.config.WebSecurityConfig;
import io.dkargo.bulletinboard.dto.request.user.ReqAddUserDTO;
import io.dkargo.bulletinboard.entity.User;
import io.dkargo.bulletinboard.repository.UserRepository;
import io.dkargo.bulletinboard.repository.support.UserRepositorySupport;
import io.dkargo.bulletinboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserRepositorySupport userRepositorySupport;
    private final WebSecurityConfig webSecurityConfig;

    //회원 추가
    @Override
    public void addUser(ReqAddUserDTO reqAddUserDTO) {

        User user = userRepositorySupport.findUserByUserMail(reqAddUserDTO.getUserEmail());

        if (user != null) {
            throw new RuntimeException("해당 이메일은 이미 사용중 입니다.");
        }

        user = User.builder()
                .userName(reqAddUserDTO.getUserName())
                .userMail(reqAddUserDTO.getUserEmail())
                .userPassword(reqAddUserDTO.getUserPassword())
                .build();

        user.encryptPassword(webSecurityConfig.passwordEncoder());

        userRepository.save(user);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user =  userRepository.findById(1L).orElseThrow();
//        return new UserDetail(user);
//    }
}
