package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.user.ReqCreateUserDTO;
import io.dkargo.bulletinboard.dto.request.user.ReqUserLoginDTO;
import io.dkargo.bulletinboard.dto.request.user.UserTokenDTO;
import io.dkargo.bulletinboard.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원 추가")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(
            @Parameter(ref = "유저 이름", required = true) @RequestBody @Valid ReqCreateUserDTO reqCreateUserDTO) {
        userService.createUser(reqCreateUserDTO);
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public UserTokenDTO currentUserName(@RequestBody ReqUserLoginDTO reqUserLoginDTO) {
        return userService.login(reqUserLoginDTO.getEmail(), reqUserLoginDTO.getPassword());
    }

    @Operation(summary = "유저 이름 조회")
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_USER")
    public String name() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication().getName();
    }

    @Operation(summary = "리프레시 토큰 발급")
    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.OK)
    public UserTokenDTO reissue(@RequestBody UserTokenDTO userTokenDTO) {
        return userService.reissue(userTokenDTO.getAccessToken(), userTokenDTO.getRefreshToken());
    }


}
