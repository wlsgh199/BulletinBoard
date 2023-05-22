package io.dkargo.bulletinboard.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dkargo.bulletinboard.dto.request.member.ReqMemberLoginDTO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@RequiredArgsConstructor
public class CustomLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    //TODO : AbstractAuthenticationProcessingFilter 고려
//    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    public CustomLoginAuthenticationFilter(ObjectMapper objectMapper) {
        // 인증을 처리할 URL을 지정
        super(new AntPathRequestMatcher("/login", "POST"));
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        // 사용자로부터 전달받은 인증 정보를 추출하고, 인증 처리를 수행
        ReqMemberLoginDTO reqMemberLoginDTO = objectMapper.readValue(request.getInputStream(), ReqMemberLoginDTO.class);
        // 실제 인증 처리를 위해 Authentication 객체를 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(reqMemberLoginDTO.getEmail(), reqMemberLoginDTO.getPassword());

        // AuthenticationManager에 인증 처리를 위임합니다.
        return getAuthenticationManager().authenticate(authentication);
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

//        // Request Header 에서 토큰 정보 추출
//        private String resolveToken (HttpServletRequest request){
//            String bearerToken = request.getHeader("Authorization");
//            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) { //TODO : 뒤 체크
//                return bearerToken.substring(7);
//            }
//            return null;
//        }

    }