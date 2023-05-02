package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.ReqUserDTO;
import io.dkargo.bulletinboard.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/members")
@Transactional
public class MemberController {

    private final UserService userService;

    public MemberController(UserService userService) {
        this.userService = userService;
    }


    @ApiOperation(value = "회원 추가")
    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void saveMember(
            @ApiParam(value = "유저 이름", required = true) @RequestBody ReqUserDTO reqUserDTO) {
        userService.saveMember(reqUserDTO);
    }
}
