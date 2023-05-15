package io.dkargo.bulletinboard.config;

import io.dkargo.bulletinboard.filter.CustomAuthFailureHandler;
import io.dkargo.bulletinboard.filter.CustomAuthSuccessHandler;
import io.dkargo.bulletinboard.jwt.JwtAuthenticationFilter;
import io.dkargo.bulletinboard.jwt.JwtTokenProvider;
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
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final CorsFilter corsFilter;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
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
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthFailureHandler();
    }


//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        http.csrf().disable();
//        http.authorizeRequests()
//                .antMatchers("/v3/api-docs/**",
//                        "/swagger-ui/**",
//                        "/swagger-ui.html").permitAll()
//                .anyRequest().authenticated()
////                        .antMatchers("/users/**").authenticated()
////                        .antMatchers("/posts/**").access("hasRole('ROLE_ADMIN')")
////                        .anyRequest().permitAll()
//                .and()
//                .formLogin()
////                .loginProcessingUrl("/login")
//                .usernameParameter("email")
//                .successHandler(authenticationSuccessHandler())
//                .failureHandler(authenticationFailureHandler());
////                .defaultSuccessUrl("/swagger-ui/index.html");


        http.csrf().disable();
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션을 사용하지 않겠다.
                .and()
                .addFilter(corsFilter)
                .formLogin()
                .disable()
                .httpBasic().disable()
                .authorizeRequests()
//                .antMatchers("/users/**")
//                .access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')") //
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);



        return http.build();
    }



}
