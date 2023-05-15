package io.dkargo.bulletinboard.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderByListEnum {

    //TODO : 정렬 리팩토링해야함
    ORDER_BY_member_id_DESC,
    ORDER_BY_CATEGORY_ID_DESC,
    ORDER_BY_TITLE_DESC,
    ORDER_BY_CONTENT_DESC,
    ORDER_BY_POST_ID_DESC,
    ORDER_BY_member_id_ASC,
    ORDER_BY_CATEGORY_ID_ASC,
    ORDER_BY_TITLE_ASC,
    ORDER_BY_CONTENT_ASC,
    ORDER_BY_POST_ID_ASC

// jsoncreator annotation
}
