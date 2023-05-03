package io.dkargo.bulletinboard.repository.support;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.dkargo.bulletinboard.dto.common.OrderByListEnum;
import io.dkargo.bulletinboard.dto.request.post.ReqGetDTO;
import io.dkargo.bulletinboard.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static io.dkargo.bulletinboard.entity.QPost.post;
import static io.dkargo.bulletinboard.entity.QPostFile.postFile;
import static io.dkargo.bulletinboard.entity.QUser.user;
import static io.dkargo.bulletinboard.entity.QComment.comment;
import static io.dkargo.bulletinboard.entity.QPostCategory.postCategory;

@Repository
public class PostRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public PostRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Post.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Post findDetailPostById(Long id) {
        //게시물 상세조회 리턴
        return jpaQueryFactory
                .selectFrom(post)
                .leftJoin(post.user, user)
                .leftJoin(post.postFileList, postFile)
                .fetchJoin()
                .where(post.id.eq(id))
                .fetchOne();
    }

    public void incrementClickCount(Long id) {
        // 클릭 횟수 증가
        jpaQueryFactory.update(post)
                .set(post.clickCount, post.clickCount.add(1L))
                .where(post.id.eq(id))
                .execute();
    }

    public List<Post> findPostByReqGetDTO(ReqGetDTO reqGetDTO) {
        return jpaQueryFactory.selectFrom(post)
                .distinct()
                .leftJoin(post.postCategoryList, postCategory)
                .leftJoin(post.commentList, comment)
                .fetchJoin()
                .where(containsContent(reqGetDTO.getContent()),
                        containsTitle(reqGetDTO.getTitle()),
                        eqCategoryId(reqGetDTO.getCategoryId()),
                        eqUserId(reqGetDTO.getUserId())
                )
                .orderBy(selectSort(reqGetDTO.getOrderByListEnum()))
                .offset(reqGetDTO.getPage())
                .limit(reqGetDTO.getSize())
                .fetch();
    }

    private BooleanExpression eqUserId(Long userId) {
        if (userId == null) {
            return null;
        }
        return post.user.id.eq(userId);
    }

    private BooleanExpression eqCategoryId(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return QPostCategory.postCategory.category.id.eq(categoryId);
    }

    private BooleanExpression containsTitle(String title) {
        if (!StringUtils.hasText(title)) {
            return null;
        }
        return post.title.contains(title);
    }

    private BooleanExpression containsContent(String content) {
        if (!StringUtils.hasText(content)) {
            return null;
        }
        return post.content.contains(content);
    }

    private OrderSpecifier<?> selectSort(OrderByListEnum orderByListEnum) {
        switch (orderByListEnum) {
            case ORDER_BY_POST_ID_DESC: {
                return new OrderSpecifier<>(Order.DESC, post.id);
            }
            case ORDER_BY_CATEGORY_ID_DESC: {
                return new OrderSpecifier<>(Order.DESC, postCategory.category.id);
            }
            case ORDER_BY_TITLE_DESC: {
                return new OrderSpecifier<>(Order.DESC, post.title);
            }
            case ORDER_BY_CONTENT_DESC: {
                return new OrderSpecifier<>(Order.DESC, post.content);
            }
            case ORDER_BY_USER_ID_DESC: {
                return new OrderSpecifier<>(Order.DESC, post.user.id);
            }
            case ORDER_BY_TITLE_ASC: {
                return new OrderSpecifier<>(Order.ASC, post.title);
            }
            case ORDER_BY_CONTENT_ASC: {
                return new OrderSpecifier<>(Order.ASC, post.content);
            }
            case ORDER_BY_USER_ID_ASC: {
                return new OrderSpecifier<>(Order.ASC, post.user.id);
            }
            case ORDER_BY_CATEGORY_ID_ASC: {
                return new OrderSpecifier<>(Order.ASC, postCategory.id);
            }
            case ORDER_BY_POST_ID_ASC: {
                return new OrderSpecifier<>(Order.ASC, post.id);
            }

        }
        return null;
    }
}
