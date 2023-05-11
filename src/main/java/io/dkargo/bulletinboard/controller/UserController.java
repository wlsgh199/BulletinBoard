package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.user.ReqCreateUserDTO;
import io.dkargo.bulletinboard.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
    public void addUser(
            @Parameter(ref = "유저 이름", required = true) @RequestBody @Valid ReqCreateUserDTO reqCreateUserDTO) {
        userService.createUser(reqCreateUserDTO);
    }

    @Operation(summary = "유저이름 조회")
    @GetMapping("/name")
    @ResponseStatus(HttpStatus.OK)
    public String currentUserName(Authentication authentication)  {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
