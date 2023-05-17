package io.dkargo.bulletinboard.dto.response.member;

import io.dkargo.bulletinboard.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema
@Getter
@Setter
public class ResUpdateMemberDTO {
    private String name;

    public ResUpdateMemberDTO(Member member) {
        this.name = member.getName();
    }
}
