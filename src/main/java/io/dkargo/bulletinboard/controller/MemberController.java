package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.ReqMemberDTO;
import io.dkargo.bulletinboard.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    public void saveMember(@RequestBody ReqMemberDTO reqMemberDTO) {
        memberService.saveMember(reqMemberDTO);
    }
}
