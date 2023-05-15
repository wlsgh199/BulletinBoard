package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.dto.common.MemberAdapter;
import io.dkargo.bulletinboard.dto.request.user.ReqCreateMemberDTO;
import io.dkargo.bulletinboard.dto.request.user.ReqMemberLoginDTO;
import io.dkargo.bulletinboard.dto.request.user.MemberTokenDTO;
import io.dkargo.bulletinboard.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 추가")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMember(
            @Parameter(ref = "유저 이름", required = true) @RequestBody @Valid ReqCreateMemberDTO reqCreateMemberDTO) {
        memberService.createUser(reqCreateMemberDTO);
    }

    @Operation(summary = "로그인한 유저 정보 조회")
    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
//    @Secured("ROLE_USER")
    public String info() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return securityContext.getAuthentication().getName();
    }

    @Operation(summary = "로그인")
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public MemberTokenDTO login(@RequestBody ReqMemberLoginDTO reqMemberLoginDTO) {
        return memberService.login(reqMemberLoginDTO.getEmail(), reqMemberLoginDTO.getPassword());
    }

    @Operation(summary = "리프레시 토큰 발급")
    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.OK)
    public MemberTokenDTO reissue(@RequestBody MemberTokenDTO memberTokenDTO) {
        return memberService.reissue(memberTokenDTO.getAccessToken(), memberTokenDTO.getRefreshToken());
    }

    @Operation(summary = "회원탈퇴")
    @PostMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMember(@AuthenticationPrincipal User member) {
        System.out.println("member = " + member.getUsername());
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//        userService.deleteUser(securityContext.getAuthentication().getName());
    }


}
