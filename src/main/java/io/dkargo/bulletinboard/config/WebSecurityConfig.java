package io.dkargo.bulletinboard.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.dkargo.bulletinboard.filter.handler.JwtAuthFailureHandler;
import io.dkargo.bulletinboard.filter.handler.LoginAuthFailureHandler;
import io.dkargo.bulletinboard.filter.handler.LoginAuthSuccessHandler;
import io.dkargo.bulletinboard.filter.JwtAuthenticationFilter;
import io.dkargo.bulletinboard.filter.LoginAuthenticationFilter;
import io.dkargo.bulletinboard.jwt.JwtTokenProvider;
import io.dkargo.bulletinboard.jwt.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,  //@Secured 사용가능
        prePostEnabled = false, //@PreAuthorize 사용가능
        jsr250Enabled = false) //@RolesAllowed 사용가능
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final CorsFilter corsFilter;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final ObjectMapper objectMapper;
    private final RedisUtil redisUtil;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new LoginAuthSuccessHandler(jwtTokenProvider, objectMapper, redisUtil);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new LoginAuthFailureHandler();
    }

    @Bean
    public LoginAuthenticationFilter loginAuthenticationFilter() throws Exception {
        LoginAuthenticationFilter loginAuthenticationFilter = new LoginAuthenticationFilter(objectMapper);
        loginAuthenticationFilter.setAuthenticationManager(authenticationManager());
        loginAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        loginAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
        return loginAuthenticationFilter;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
//        CustomRequestMatcher customRequestMatcher = new CustomRequestMatcher("ROLE_USER");
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(new AntPathRequestMatcher("/members/**"), jwtTokenProvider);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager());
//        jwtAuthenticationFilter.setAuthenticationSuccessHandler(new JwtAuthSuccessHandler());
        jwtAuthenticationFilter.setAuthenticationFailureHandler(new JwtAuthFailureHandler());
        jwtAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
//
        return jwtAuthenticationFilter;

    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();// TODO : 세션, 레스트 api 차이 조사
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션을 사용하지 않겠다.
                .and()
                .addFilter(corsFilter)
                .formLogin()
                .disable()
                .httpBasic().disable()
                .authorizeRequests()
//                .antMatchers("/members/**")
//                .access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
//                .antMatchers("/posts/**")
//                .access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
//                .antMatchers("/categories/**")
//                .access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(loginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }


}
