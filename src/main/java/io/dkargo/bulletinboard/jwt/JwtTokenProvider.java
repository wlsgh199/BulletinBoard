package io.dkargo.bulletinboard.jwt;

import io.dkargo.bulletinboard.dto.response.member.ResMemberTokenDTO;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.exception.CustomException;
import io.dkargo.bulletinboard.exception.ErrorCodeEnum;
import io.dkargo.bulletinboard.repository.MemberRepository;
import io.dkargo.bulletinboard.service.Impl.MemberServiceImpl;
import io.dkargo.bulletinboard.service.MemberService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final int accessTokenTTL;
    private final int refreshTokenTTL;

    private final RedisUtil redisUtil;
    private final Key key;
    private final MemberRepository memberRepository;

    public JwtTokenProvider(RedisUtil redisUtil,
                            MemberRepository memberRepository,
                            @Value("${jwt.secret}") String secret,
                            @Value("${jwt.validity-minutes.access-token}") int accessTokenTTL,
                            @Value("${jwt.validity-minutes.refresh-token}") int refreshTokenTTL) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.redisUtil = redisUtil;
        this.memberRepository = memberRepository;
        this.accessTokenTTL = accessTokenTTL * 60 * 1000;
        this.refreshTokenTTL = refreshTokenTTL * 60 * 1000;
    }

    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
    public ResMemberTokenDTO generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + accessTokenTTL);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + refreshTokenTTL))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        ResMemberTokenDTO resMemberTokenDTO = new ResMemberTokenDTO();
//        resMemberTokenDTO.setGrantType("Bearer");
        resMemberTokenDTO.setAccessToken(accessToken);
        resMemberTokenDTO.setRefreshToken(refreshToken);

        return resMemberTokenDTO;
    }

    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            // TODO : Filter 단 에러처리 필요함.
            throw new CustomException(ErrorCodeEnum.INVALID_AUTH_TOKEN);
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(org.springframework.security.core.authority.SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // MemberAdapter 객체를 만들어서 Authentication 리턴
        Member member = memberRepository.findUserByEmail(claims.getSubject());
        if (member == null) {
            throw new CustomException(ErrorCodeEnum.MEMBER_NOT_FOUND);
        }
        return new UsernamePasswordAuthenticationToken(new MemberAdapter(member), null, authorities);
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
//        try {
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        if (redisUtil.hasKeyBlackList(token)) {
            throw new CustomException(ErrorCodeEnum.INVALID_AUTH_TOKEN);
        }
        return true;
//        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
//            log.info("Invalid JWT Token", e);
//        } catch (ExpiredJwtException e) {
//            log.info("Expired JWT Token", e);
//        } catch (UnsupportedJwtException e) {
//            log.info("Unsupported JWT Token", e);
//        } catch (IllegalArgumentException e) {
//            log.info("JWT claims string is empty.", e);
//        }
//        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}