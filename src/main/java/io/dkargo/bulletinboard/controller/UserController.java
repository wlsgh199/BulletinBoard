package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.user.ReqAddUserDTO;
import io.dkargo.bulletinboard.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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


    @ApiOperation(value = "회원 추가")
    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void saveMember(
            @ApiParam(value = "유저 이름", required = true) @RequestBody @Valid ReqAddUserDTO reqAddUserDTO) {
        userService.saveMember(reqAddUserDTO);
    }
}
