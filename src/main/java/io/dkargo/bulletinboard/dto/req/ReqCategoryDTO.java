package io.dkargo.bulletinboard.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReqCategoryDTO {

    /**
     * 카테고리 부모 아이디
     */
    private Integer parentId;

    /**
     * 카테고리 이름
     */
    private String categoryName;

    /**
     * 깊이
     */
    private Integer depth;
}
