package io.dkargo.bulletinboard.repository.support;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.dkargo.bulletinboard.entity.Category;
import io.dkargo.bulletinboard.entity.Comment;
import io.dkargo.bulletinboard.exception.CustomException;
import io.dkargo.bulletinboard.exception.ErrorCodeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static io.dkargo.bulletinboard.entity.QCategory.category;
import static io.dkargo.bulletinboard.entity.QComment.comment;
import static io.dkargo.bulletinboard.entity.QPost.post;
import static io.dkargo.bulletinboard.entity.QPostCategory.postCategory;
import static io.dkargo.bulletinboard.entity.QReply.reply;

@Repository
public class CategoryRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public CategoryRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Category.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    // 카테고리 이미 존재하는지 체크
    public void existCategoryCheck(Integer parentId, String categoryName) {
        Integer result = jpaQueryFactory.selectOne()
                .from(category)
                .where(eqParentId(parentId), eqCategoryName(categoryName))
                .fetchFirst();

        if (result != null) {
            throw new CustomException(ErrorCodeEnum.DUPLICATE_CATEGORY);
        }
    }

    private BooleanExpression eqParentId(Integer parentId) {
        if(parentId == null) {
            return null;
        }
        return category.parentId.eq(parentId);
    }

    private BooleanExpression eqCategoryName(String categoryName) {
        if (!StringUtils.hasText(categoryName)) {
            return null;
        }
        return category.categoryName.eq(categoryName);
    }

}
