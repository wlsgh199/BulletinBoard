package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.config.JwtTokenProvider;
import io.dkargo.bulletinboard.config.WebSecurityConfig;
import io.dkargo.bulletinboard.dto.common.PrincipalDetails;
import io.dkargo.bulletinboard.dto.request.user.ReqCreateUserDTO;
import io.dkargo.bulletinboard.dto.request.user.UserTokenDTO;
import io.dkargo.bulletinboard.entity.User;
import io.dkargo.bulletinboard.repository.UserRepository;
import io.dkargo.bulletinboard.repository.support.UserRepositorySupport;
import io.dkargo.bulletinboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService , UserDetailsService {

    private final UserRepository userRepository;
    private final UserRepositorySupport userRepositorySupport;
    private final WebSecurityConfig webSecurityConfig;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findUserByUserEmail(email);

        if(user != null) {
            return new PrincipalDetails(user);
        }
        throw new UsernameNotFoundException("");
    }

    @Override
    public UserTokenDTO login(String email, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        System.out.println("authenticationToken.isAuthenticated() = " + authenticationToken.isAuthenticated());
        System.out.println("authenticationToken.getName() = " + authenticationToken.getName());
        System.out.println("authenticationToken.getCredentials() = " + authenticationToken.getCredentials());
        System.out.println("authenticationToken.getPrincipal() = " + authenticationToken.getPrincipal());
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        return jwtTokenProvider.generateToken(authentication);
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
