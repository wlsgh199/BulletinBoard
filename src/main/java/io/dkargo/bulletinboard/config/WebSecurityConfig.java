package io.dkargo.bulletinboard.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        User.UserBuilder users = User.builder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("{noop}user").roles("USER").build());
        manager.createUser(users.username("admin").password("{noop}user").roles("USER", "ADMIN").build());
        return manager;
    }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{noop}user")
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{noop}admin")
//                .roles("ADMIN")
//                .build();
//
//        List<UserDetails> userDetailsList = new ArrayList<>();
//        userDetailsList.add(user);
//        userDetailsList.add(admin);
//
//        return new InMemoryUserDetailsManager(userDetailsList);
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                // login 없이 접근 허용 하는 url
                .antMatchers("/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/");
//                .usernameParameter("user")
//                .passwordParameter("user")
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(true)
//                .expiredUrl("/expired");
//                .and()
//                .logout()
////                .logoutUrl("/") // 로그아웃 URL 설정
////                .logoutSuccessUrl("/") // 로그아웃 성공 후 이동할 URL 설정
//                .invalidateHttpSession(true) // 로그아웃 후 세션 초기화 설정
//                .deleteCookies("JSESSIONID"); // 로그아웃 후 쿠기 삭제 설정

        return http.build();
    }

    //  http.authorizeRequests()
    //                // login 없이 접근 허용 하는 url
    //                .antMatchers("/auth/**").permitAll()
    //                // '/admin'의 경우 ADMIN 권한이 있는 사용자만 접근이 가능
    //                .antMatchers("/admin").hasRole("ADMIN")
    //                // 그 외 모든 요청은 인증과정 필요
    //                .anyRequest().authenticated();
}
