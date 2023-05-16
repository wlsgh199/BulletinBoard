package io.dkargo.bulletinboard.dto.common;

import com.querydsl.core.types.Order;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderByListEnum {
    MEMBER_ID("member_id"),
    CATEGORY_ID("category_id"),
    TITLE("title"),
    CONTENT("content"),
    POST_ID("post_id");

    private final String name;
}
