package io.dkargo.bulletinboard.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dkargo.bulletinboard.dto.request.member.ReqMemberLoginDTO;
import io.dkargo.bulletinboard.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomJwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    //TODO : AbstractAuthenticationProcessingFilter 고려
    private final JwtTokenProvider jwtTokenProvider;
//    private final ObjectMapper objectMapper;

    public CustomJwtAuthenticationFilter(RequestMatcher requestMatcher, JwtTokenProvider jwtTokenProvider) {

        // 인증을 처리할 URL을 지정
        super(requestMatcher);
//        this.objectMapper = objectMapper;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // 1. Request Header 에서 JWT 토큰 추출
        String token = resolveToken(request);
        // 2. validateToken 으로 토큰 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장

            Authentication authentication = jwtTokenProvider.getAuthentication(token);
//            System.out.println("authenticationToken.isAuthenticated() = " + authentication.isAuthenticated());
//            System.out.println("authenticationToken.getName() = " + authentication.getName());
//            System.out.println("authenticationToken.getCredentials() = " + authentication.getCredentials());
//            System.out.println("authenticationToken.getPrincipal() = " + authentication.getPrincipal());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        }
        return null;
    }
 /*   @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. Request Header 에서 JWT 토큰 추출
        String token = resolveToken(request);
        // 2. validateToken 으로 토큰 유효성 검사
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장

            Authentication authentication = jwtTokenProvider.getAuthentication(token);
//            System.out.println("authenticationToken.isAuthenticated() = " + authentication.isAuthenticated());
//            System.out.println("authenticationToken.getName() = " + authentication.getName());
//            System.out.println("authenticationToken.getCredentials() = " + authentication.getCredentials());
//            System.out.println("authenticationToken.getPrincipal() = " + authentication.getPrincipal());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }*/

        // Request Header 에서 토큰 정보 추출
        private String resolveToken (HttpServletRequest request){
            String bearerToken = request.getHeader("Authorization");
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) { //TODO : 뒤 체크
                return bearerToken.substring(7);
            }
            return null;
        }

}