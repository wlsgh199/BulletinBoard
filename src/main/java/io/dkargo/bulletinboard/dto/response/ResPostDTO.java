package io.dkargo.bulletinboard.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@ApiModel
@Getter @Setter
@Builder
public class ResPostDTO {
    private Long postId;
}
