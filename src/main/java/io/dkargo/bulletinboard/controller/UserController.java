package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.config.WebSecurityConfig;
import io.dkargo.bulletinboard.dto.request.user.ReqAddUserDTO;
import io.dkargo.bulletinboard.service.UserService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원 추가")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void addUser(
            @Parameter(ref = "유저 이름", required = true) @RequestBody @Valid ReqAddUserDTO reqAddUserDTO) {
        userService.addUser(reqAddUserDTO);
    }

    @Operation(summary = "로그인")
    @GetMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserDetails loadUser() throws UsernameNotFoundException {
        return userService.loadUserByUsername("jhpark@dkargo.io");
    }
}
