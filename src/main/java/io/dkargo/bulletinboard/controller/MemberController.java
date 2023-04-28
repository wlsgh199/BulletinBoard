package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.ReqMemberDTO;
import io.dkargo.bulletinboard.service.MemberService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @ApiOperation(value = "회원 추가")
    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void saveMember(
            @ApiParam(value = "유저 이름", required = true) @RequestBody ReqMemberDTO reqMemberDTO) {
        memberService.saveMember(reqMemberDTO);
    }
}
