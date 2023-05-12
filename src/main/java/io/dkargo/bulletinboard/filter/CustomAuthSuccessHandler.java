package io.dkargo.bulletinboard.filter;

import ch.qos.logback.core.util.ContentTypeUtil;
import lombok.extern.slf4j.Slf4j;
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
public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

//        try( PrintWriter writer = response.getWriter()) {
//
//            Jwt jwt = JwtManager.createJwt((String) authentication.getPrincipal());
//            JsonObject json = new JsonObject();
//            json.addProperty("accessToken",jwt.getEncoded());
//
//            response.setStatus(HttpStatus.ACCEPTED.value());
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
//
//            writer.write(json.toString());
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        System.out.println(" okokokok");
        response.setStatus(HttpStatus.ACCEPTED.value());
        response.setContentType(MediaType.TEXT_HTML_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        PrintWriter writer = response.getWriter();
        writer.write("login OK 한글 ok");
        writer.close();
    }
}
