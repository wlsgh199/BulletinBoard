package io.dkargo.bulletinboard.service.Impl;

import io.dkargo.bulletinboard.config.WebSecurityConfig;
import io.dkargo.bulletinboard.dto.request.member.ReqCreateMemberDTO;
import io.dkargo.bulletinboard.dto.response.member.ResCreateMemberDTO;
import io.dkargo.bulletinboard.dto.response.member.ResFindMemberDTO;
import io.dkargo.bulletinboard.dto.response.member.ResMemberTokenDTO;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.exception.CustomException;
import io.dkargo.bulletinboard.exception.ErrorCodeEnum;
import io.dkargo.bulletinboard.jwt.JwtTokenProvider;
import io.dkargo.bulletinboard.jwt.MemberAdapter;
import io.dkargo.bulletinboard.jwt.RedisUtil;
import io.dkargo.bulletinboard.repository.MemberRepository;
import io.dkargo.bulletinboard.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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
public class MemberServiceImpl implements MemberService, UserDetailsService {

    @Value("${jwt.validity-minutes.access-token}")
    private int accessTokenTTL;

    @Value("${jwt.validity-minutes.refresh-token}")
    private int refreshTokenTTL;

    private final MemberRepository memberRepository;
    private final WebSecurityConfig webSecurityConfig;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisUtil redisUtil;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findUserByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException(email);
        }

        return new MemberAdapter(member);
    }

    @Override
    public ResMemberTokenDTO login(String email, String password) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        ResMemberTokenDTO resMemberTokenDTO = jwtTokenProvider.generateToken(authentication);
        redisUtil.set(email, resMemberTokenDTO, 60);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        return resMemberTokenDTO;
    }

    @Override
    public void logout(String accessToken, String refreshToken) {
        redisUtil.setBlackList(accessToken, "accessToken", accessTokenTTL);
        redisUtil.setBlackList(refreshToken, "refreshToken", refreshTokenTTL);
    }


    // 토큰 재발급 관련 메서드
    @Override
    public ResMemberTokenDTO reissue(String accessToken, String refreshToken) {
        jwtTokenProvider.validateToken(refreshToken);
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);

        redisUtil.setBlackList(accessToken, "accessToken", accessTokenTTL);
        redisUtil.setBlackList(refreshToken, "refreshToken", refreshTokenTTL);
        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public ResFindMemberDTO findMember(Member member) {
        return new ResFindMemberDTO(member);
    }

    @Override
    public void grantAdmin(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCodeEnum.MEMBER_NOT_FOUND));

        member.grantAdmin();
    }


    //회원 추가
    @Override
    public ResCreateMemberDTO createUser(ReqCreateMemberDTO reqCreateMemberDTO) {

        Member member = memberRepository.findUserByEmail(reqCreateMemberDTO.getEmail());

        if (member != null) {
            throw new CustomException(ErrorCodeEnum.DUPLICATE_EMAIL);
        }

        member = Member.builder()
                .name(reqCreateMemberDTO.getName())
                .email(reqCreateMemberDTO.getEmail())
                .password(reqCreateMemberDTO.getPassword())
                .build();

        member.encryptPassword(webSecurityConfig.passwordEncoder());
        memberRepository.save(member);

        return new ResCreateMemberDTO(member);
    }

    @Override
    public void deleteUserById(long memberId) {
        memberRepository.deleteById(memberId);
        SecurityContextHolder.clearContext();
    }
}
