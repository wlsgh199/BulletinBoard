package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.config.WebSecurityConfig;
import io.dkargo.bulletinboard.dto.request.user.ReqCreateUserDTO;
import io.dkargo.bulletinboard.entity.User;
import io.dkargo.bulletinboard.dto.common.PrincipalDetails;
import io.dkargo.bulletinboard.repository.UserRepository;
import io.dkargo.bulletinboard.repository.support.UserRepositorySupport;
import io.dkargo.bulletinboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final UserRepositorySupport userRepositorySupport;
    private final WebSecurityConfig webSecurityConfig;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findUserByUserEmail(email);

        if(user != null) {
            return new PrincipalDetails(user);
        }
        throw new UsernameNotFoundException("");
    }


    //회원 추가
    @Override
    public void createUser(ReqCreateUserDTO reqCreateUserDTO) {

        User user = userRepositorySupport.findUserByUserMail(reqCreateUserDTO.getUserEmail());

        if (user != null) {
            throw new RuntimeException("해당 이메일은 이미 사용중 입니다.");
        }

        user = User.builder()
                .userName(reqCreateUserDTO.getUserName())
                .userMail(reqCreateUserDTO.getUserEmail())
                .userPassword(reqCreateUserDTO.getUserPassword())
                .build();

        user.encryptPassword(webSecurityConfig.passwordEncoder());

        userRepository.save(user);
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepositorySupport.findUserByUserMail(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
//        }
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        if ("admin".equals(username)) {
//            authorities.add(new SimpleGrantedAuthority(UserRoleEnum.ADMIN.getValue()));
//        } else {
//            authorities.add(new SimpleGrantedAuthority(UserRoleEnum.USER.getValue()));
//        }
//        return new org.springframework.security.core.userdetails.User("user", "user", authorities);
//    }
}
