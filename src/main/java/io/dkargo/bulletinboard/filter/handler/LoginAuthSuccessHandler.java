package io.dkargo.bulletinboard.filter.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dkargo.bulletinboard.dto.response.member.ResMemberTokenDTO;
import io.dkargo.bulletinboard.jwt.JwtTokenProvider;
import io.dkargo.bulletinboard.jwt.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
public class LoginAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final RedisUtil redisUtil;

    @Value("${jwt.validity-minutes.access-token}")
    private int ACCESS_TOKEN_TTL;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        ResMemberTokenDTO resMemberTokenDTO = jwtTokenProvider.generateToken(authentication);
        redisUtil.set(authentication.getName(), resMemberTokenDTO, ACCESS_TOKEN_TTL);

        response.setStatus(HttpStatus.ACCEPTED.value());
        response.setContentType(MediaType.TEXT_HTML_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(resMemberTokenDTO));
        writer.close();
    }
}
