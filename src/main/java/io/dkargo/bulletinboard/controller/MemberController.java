package io.dkargo.bulletinboard.controller;

import io.dkargo.bulletinboard.annotation.CurrentMember;
import io.dkargo.bulletinboard.dto.request.member.*;
import io.dkargo.bulletinboard.dto.response.member.ResMemberTokenDTO;
import io.dkargo.bulletinboard.dto.response.member.ResCreateMemberDTO;
import io.dkargo.bulletinboard.dto.response.member.ResFindMemberDTO;
import io.dkargo.bulletinboard.dto.response.member.ResUpdateMemberDTO;
import io.dkargo.bulletinboard.jwt.MemberDetailsDTO;
import io.dkargo.bulletinboard.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원 가입")
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResCreateMemberDTO createMember(
            @Parameter(ref = "유저 이름", required = true) @RequestBody @Valid ReqCreateMemberDTO reqCreateMemberDTO) {
        return memberService.createUser(reqCreateMemberDTO);
    }

    @Operation(summary = "로그인한 유저 정보 조회")
    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public ResFindMemberDTO info(@CurrentMember MemberDetailsDTO memberDetailsDTO) {
        return memberService.findMember(memberDetailsDTO.getUsername());
    }

//    @Operation(summary = "로그인")
//    @PostMapping("/login")
//    @ResponseStatus(HttpStatus.OK)
//    public ResMemberTokenDTO login(@RequestBody ReqMemberLoginDTO reqMemberLoginDTO) {
//        return memberService.login(reqMemberLoginDTO.getEmail(), reqMemberLoginDTO.getPassword());
//    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_USER")
    public void logout(@RequestBody @Valid ReqLogoutMemberDTO reqLogoutMemberDTO) {
        memberService.logout(reqLogoutMemberDTO.getAccessToken(), reqLogoutMemberDTO.getRefreshToken());
    }


    @Operation(summary = "리프레쉬 토큰 발급")
    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_USER")
    public ResMemberTokenDTO reissue(@RequestBody ReqReissueTokenDTO resReissueTokenDTO) {
        return memberService.reissue(resReissueTokenDTO.getAccessToken(), resReissueTokenDTO.getRefreshToken());
    }

    @Operation(summary = "회원정보 수정")
    @PutMapping("")
    @ResponseStatus(HttpStatus.OK)
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResUpdateMemberDTO updateMember(@CurrentMember MemberDetailsDTO memberDetailsDTO,
                                           @RequestBody @Valid ReqUpdateMemberDTO reqUpdateMemberDTO) {
        return memberService.updateMember(reqUpdateMemberDTO, memberDetailsDTO.getUsername());
    }

    @Operation(summary = "회원탈퇴")
    @DeleteMapping("")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_USER")
    public void deleteMember(@CurrentMember MemberDetailsDTO memberDetailsDTO) {
        memberService.deleteUserById(memberDetailsDTO.getUsername());
        SecurityContextHolder.getContext().getAuthentication();
    }

    @Operation(summary = "어드민 권한 부여")
    @PostMapping("/admin/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    @Secured("ROLE_ADMIN")
    public void grantAdmin(@PathVariable long memberId) {
        memberService.grantAdmin(memberId);
    }


}
