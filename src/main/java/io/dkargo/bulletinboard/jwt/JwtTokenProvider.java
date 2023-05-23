package io.dkargo.bulletinboard.jwt;

import io.dkargo.bulletinboard.dto.response.member.ResMemberTokenDTO;
import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.exception.CustomException;
import io.dkargo.bulletinboard.exception.ErrorCodeEnum;
import io.dkargo.bulletinboard.repository.MemberRepository;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final int accessTokenTTL;
    private final int refreshTokenTTL;
    private final RedisUtil redisUtil;
    //    private final Key key;
    private final MemberRepository memberRepository;

    @Value("${jwt.secret}")
    String SECRET;

    public JwtTokenProvider(RedisUtil redisUtil,
                            MemberRepository memberRepository,
                            @Value("${jwt.validity-minutes.access-token}") int accessTokenTTL,
                            @Value("${jwt.validity-minutes.refresh-token}") int refreshTokenTTL) {
//        byte[] keyBytes = Decoders.BASE64.decode(secret);
//        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.redisUtil = redisUtil;
        this.memberRepository = memberRepository;
        this.accessTokenTTL = accessTokenTTL * 60 * 1000;
        this.refreshTokenTTL = refreshTokenTTL * 60 * 1000;
    }

//    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
//    public ResMemberTokenDTO generateToken(Authentication authentication) {
//        // 권한 가져오기
//        String authorities = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));
//        long now = (new Date()).getTime();
//        // Access Token 생성
//        Date accessTokenExpiresIn = new Date(now + accessTokenTTL);
//        String accessToken = Jwts.builder()
//                .setSubject(authentication.getName())
//                .claim("auth", authorities)
//                .setExpiration(accessTokenExpiresIn)
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//
//        // Refresh Token 생성
//        String refreshToken = Jwts.builder()
//                .setExpiration(new Date(now + refreshTokenTTL))
//                .signWith(key, SignatureAlgorithm.HS256)
//                .compact();
//
//        ResMemberTokenDTO resMemberTokenDTO = new ResMemberTokenDTO();
////        resMemberTokenDTO.setGrantType("Bearer");
//        resMemberTokenDTO.setAccessToken(accessToken);
//        resMemberTokenDTO.setRefreshToken(refreshToken);
//
//        return resMemberTokenDTO;
//    }


    public ResMemberTokenDTO generateJwtToken(Member member) {
        // Access Token 생성
        String accessToken = Jwts.builder()
                .setSubject(member.getEmail())
                .setHeader(createHeader())
                .setClaims(createClaims(member))
                .setExpiration(createExpireDate())
                .signWith(createSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        // Access Token 생성
        String refreshToken = Jwts.builder()
                .setExpiration(createExpireDate())
                .signWith(createSigningKey(), SignatureAlgorithm.HS256)
                .compact();

        ResMemberTokenDTO resMemberTokenDTO = new ResMemberTokenDTO();
        resMemberTokenDTO.setAccessToken(accessToken);
        resMemberTokenDTO.setRefreshToken(refreshToken);

        return resMemberTokenDTO;
    }

    //알고리즘 Key 생성
    private Key createSigningKey() {
        byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(SECRET);
        return new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    //만료시간 생성
    private Date createExpireDate() {
        //TODO : 시간 변경
        return new Date(System.currentTimeMillis() + accessTokenTTL);
    }

    // Claims 생성
    private Map<String, Object> createClaims(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", member.getEmail());
        claims.put("role", "ROLE_" + member.getRole());

        return claims;
    }

    // JWT Header 생성
    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS256");

        return header;
    }


    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("role") == null) {
            throw new CustomException(ErrorCodeEnum.INVALID_AUTH_TOKEN);
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("role").toString().split(","))
                        .map(org.springframework.security.core.authority.SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

//        Member member = memberRepository.findUserByEmail(claims.getSubject());
        Member member = Member.builder()
                .email(claims.get("email").toString())
                .build();
//        if (member == null) {
//            throw new CustomException(ErrorCodeEnum.MEMBER_NOT_FOUND);
//        }
//        MemberDetailsDTO memberDetailsDTO = new MemberDetailsDTO();
//        memberDetailsDTO.setId(member.getId());
        //TODO : 들어올때 Id, Password 체크 하는 로직 추가.
        // JWT claim 에 password 추가해야함.
        return new UsernamePasswordAuthenticationToken(new MemberDetailsDTO(member), null, authorities);
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(createSigningKey()).build()
                    .parseClaimsJws(token);
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
            return Jwts.parserBuilder().setSigningKey(createSigningKey()).build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}