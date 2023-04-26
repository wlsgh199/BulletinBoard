package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.MemberDTO;
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
    public void saveMember(@RequestBody MemberDTO memberDTO) {
        memberService.saveMember(memberDTO);
    }
}
