package io.dkargo.bulletinboard.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public enum OrderByListEnum {
    ORDER_BY_USER_ID_DESC,
    ORDER_BY_CATEGORY_ID_DESC,
    ORDER_BY_TITLE_DESC,
    ORDER_BY_CONTENT_DESC,
    ORDER_BY_POST_ID_DESC,
    ORDER_BY_USER_ID_ASC,
    ORDER_BY_CATEGORY_ID_ASC,
    ORDER_BY_TITLE_ASC,
    ORDER_BY_CONTENT_ASC,
    ORDER_BY_POST_ID_ASC

// jsoncreator annotation
}
