package io.dkargo.bulletinboard.repository.support;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.dkargo.bulletinboard.dto.common.OrderByListEnum;
import io.dkargo.bulletinboard.dto.request.post.ReqFindOptionPostDTO;
import io.dkargo.bulletinboard.entity.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static io.dkargo.bulletinboard.entity.QComment.comment;
import static io.dkargo.bulletinboard.entity.QPost.post;
import static io.dkargo.bulletinboard.entity.QPostCategory.postCategory;
import static io.dkargo.bulletinboard.entity.QPostFile.postFile;
import static io.dkargo.bulletinboard.entity.QMember.member;

@Repository
public class PostRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public PostRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Post.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    //게시물 상세 조회
    public Post findDetailPostById(long id) {
        return jpaQueryFactory
                .selectFrom(post)
                .distinct()
                .join(post.member, member)
                .leftJoin(post.postFileList, postFile)
                .fetchJoin()
                .where(post.id.eq(id))
                .fetchOne();
    }

    //조회수 증가
//    public void incrementClickCount(long id) {
//        jpaQueryFactory.update(post)
//                .set(post.clickCount, post.clickCount.add(1L))
//                .where(post.id.eq(id))
//                .execute();
//    }

    //게시물 옵션 조회
    public List<Post> findPostByReqGetDTO(ReqFindOptionPostDTO reqFindOptionPostDTO) {
        return jpaQueryFactory.selectFrom(post)
                .distinct()
                .join(post.postCategoryList, postCategory)
                .leftJoin(post.commentList, comment)
                .fetchJoin()
                .where(containsContent(reqFindOptionPostDTO.getContent()),
                        containsTitle(reqFindOptionPostDTO.getTitle()),
                        eqCategoryId(reqFindOptionPostDTO.getCategoryId()),
                        eqUserId(reqFindOptionPostDTO.getUserId())
                )
                .orderBy(selectSort(reqFindOptionPostDTO.getOrderByListEnum(), reqFindOptionPostDTO.getOrder()))
                .offset(reqFindOptionPostDTO.getPage())
                .limit(reqFindOptionPostDTO.getSize())
                .fetch();
    }

    //where 조건에서 null 이 필요해서 long 말고 Long 사용
    private BooleanExpression eqUserId(Long userId) {
        if(userId == null) {
            return null;
        }

        return post.member.id.eq(userId);
    }

    private BooleanExpression eqCategoryId(Long categoryId) {
        if(categoryId == null) {
            return null;
        }
        return postCategory.category.id.eq(categoryId);
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

    //정렬 선택
    private OrderSpecifier<?> selectSort(OrderByListEnum orderByListEnum, Order order) {
        switch (orderByListEnum) {
            case TITLE: {
                return new OrderSpecifier<>(order, post.title);
            }
            case CONTENT: {
                return new OrderSpecifier<>(order, post.content);
            }
            case MEMBER_ID: {
                return new OrderSpecifier<>(order, post.member.id);
            }
            case CATEGORY_ID: {
                return new OrderSpecifier<>(order, postCategory.id);
            }
            case POST_ID: {
                return new OrderSpecifier<>(order, post.id);
            }
        }
        return null;
    }
}
