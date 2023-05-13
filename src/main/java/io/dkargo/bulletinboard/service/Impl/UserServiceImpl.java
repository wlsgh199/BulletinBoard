package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.config.JwtTokenProvider;
import io.dkargo.bulletinboard.config.WebSecurityConfig;
import io.dkargo.bulletinboard.dto.common.PrincipalDetails;
import io.dkargo.bulletinboard.dto.common.RedisUtil;
import io.dkargo.bulletinboard.dto.request.user.ReqCreateUserDTO;
import io.dkargo.bulletinboard.dto.request.user.UserTokenDTO;
import io.dkargo.bulletinboard.entity.User;
import io.dkargo.bulletinboard.exception.CustomException;
import io.dkargo.bulletinboard.exception.ErrorCodeEnum;
import io.dkargo.bulletinboard.repository.UserRepository;
import io.dkargo.bulletinboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final WebSecurityConfig webSecurityConfig;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);

        if (user != null) {
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

        UserTokenDTO userTokenDTO = jwtTokenProvider.generateToken(authentication);
        redisUtil.set(email,userTokenDTO,5);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        return userTokenDTO;
    }

    // 토큰 재발급 관련 메서드
    @Override
    public UserTokenDTO reissue(String accessToken, String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new CustomException(ErrorCodeEnum.INVALID_AUTH_TOKEN);
        }
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String authorities =
        redisUtil.setBlackList(accessToken, "accessToken", 1800);
        redisUtil.setBlackList(refreshToken, "refreshToken", 60400);
        return jwtTokenProvider.generateToken(authentication);
    }



    //회원 추가
    @Override
    public void createUser(ReqCreateUserDTO reqCreateUserDTO) {

        User user = userRepository.findUserByEmail(reqCreateUserDTO.getEmail());

        if (user != null) {
            throw new CustomException(ErrorCodeEnum.DUPLICATE_EMAIL);
        }

        user = User.builder()
                .name(reqCreateUserDTO.getName())
                .email(reqCreateUserDTO.getEmail())
                .password(reqCreateUserDTO.getPassword())
                .build();

        user.encryptPassword(webSecurityConfig.passwordEncoder());

        userRepository.save(user);
    }
}
