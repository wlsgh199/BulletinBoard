package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.request.ReqMemberDTO;
import io.dkargo.bulletinboard.service.MemberService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members")
    public void saveMember(@RequestBody ReqMemberDTO reqMemberDTO) {
        memberService.saveMember(reqMemberDTO);
    }
}
