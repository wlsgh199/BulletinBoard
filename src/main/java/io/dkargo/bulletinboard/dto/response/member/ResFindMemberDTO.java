package io.dkargo.bulletinboard.dto.response.member;

import io.dkargo.bulletinboard.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Schema
@Getter
@Setter
public class ResFindMemberDTO {
    @Schema(description = "이름")
    private String name;

    @Schema(description = "이메일")
    private String email;

    public ResFindMemberDTO(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
    }
}
