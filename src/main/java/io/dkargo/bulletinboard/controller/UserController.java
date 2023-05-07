package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.user.ReqAddUserDTO;
import io.dkargo.bulletinboard.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Transactional
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(summary = "회원 추가")
    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void addMember(
            @Parameter(ref = "유저 이름", required = true) @RequestBody @Valid ReqAddUserDTO reqAddUserDTO) {
        userService.addMember(reqAddUserDTO);
    }
}
