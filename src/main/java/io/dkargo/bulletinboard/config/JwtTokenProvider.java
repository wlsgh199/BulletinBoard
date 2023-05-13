package io.dkargo.bulletinboard.config;

import io.dkargo.bulletinboard.dto.common.RedisUtil;
import io.dkargo.bulletinboard.dto.request.user.UserTokenDTO;
import io.dkargo.bulletinboard.exception.CustomException;
import io.dkargo.bulletinboard.exception.ErrorCodeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
    //TODO : 환경변수 로 이동

    //    @Value("${jwt.secret}") String secret,
//    @Value("${jwt.access-token-validity-in-seconds}") long accessTokenValidityInSeconds,
//    @Value("${jwt.refresh-token-validity-in-seconds}") long refreshTokenValidityInSeconds,

    private final int TOKEN_TTL = 30 * 60 * 1000;   //30분
    private final RedisUtil redisUtil;
    private final Key key;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey
    , RedisUtil redisUtil) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.redisUtil = redisUtil;
    }

    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
    public UserTokenDTO generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        // Access Token 생성
        Date accessTokenExpiresIn = new Date(now + TOKEN_TTL);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + TOKEN_TTL))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        UserTokenDTO userTokenDTO = new UserTokenDTO();
        userTokenDTO.setGrantType("Bearer");
        userTokenDTO.setAccessToken(accessToken);
        userTokenDTO.setRefreshToken(refreshToken);

        return userTokenDTO;
    }

    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new CustomException(ErrorCodeEnum.INVALID_AUTH_TOKEN);
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(org.springframework.security.core.authority.SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            if (redisUtil.hasKeyBlackList(token)) {
                throw new CustomException(ErrorCodeEnum.INVALID_AUTH_TOKEN);
            }
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}