package io.dkargo.bulletinboard.repository.support;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.dkargo.bulletinboard.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public PostRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Post.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Post> findAllPost(Pageable pageable) {
        QPost post = QPost.post;
        QComment comment = QComment.comment;

        return jpaQueryFactory.selectFrom(post)
                .leftJoin(post.commentList, comment)
                .fetchJoin()
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(post.id)
                .fetch();
    }

    public Post findDetailPostById(Long id) {
        QPost post = QPost.post;
        QPostFile postFile = QPostFile.postFile;
        QUser user = QUser.user;

        // 클릭 횟수 증가
        jpaQueryFactory.update(post)
                .set(post.clickCount, post.clickCount.add(1L))
                .where(post.id.eq(id))
                .execute();

        //게시물 상세조회 리턴
        return jpaQueryFactory
                .selectFrom(post)
                .leftJoin(post.user, user)
                .leftJoin(post.postFileList, postFile)
                .fetchJoin()
                .where(post.id.eq(id))
                .fetchOne();
    }

    public List<Post> findPostByMemberId(Long userId, Pageable pageable) {
        QPost post = QPost.post;
        QComment comment = QComment.comment;

        return jpaQueryFactory.selectFrom(post)
                .leftJoin(post.commentList, comment)
                .fetchJoin()
                .where(post.user.id.eq(userId))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(post.id)
                .fetch();
    }

    public List<Post> findPostByTitle(String title, Pageable pageable) {
        QPost post = QPost.post;
        QComment comment = QComment.comment;

        return jpaQueryFactory.selectFrom(post)
                .leftJoin(post.commentList, comment)
                .fetchJoin()
                .where(post.title.contains(title))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(post.id)
                .fetch();
    }

    public List<Post> findPostByContent(String content, Pageable pageable) {
        QPost post = QPost.post;
        QComment comment = QComment.comment;

        return jpaQueryFactory.selectFrom(post)
                .leftJoin(post.commentList, comment)
                .fetchJoin()
                .where(post.content.contains(content))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(post.id)
                .fetch();
    }

    public List<Post> findPostByCategory(Long categoryId, Pageable pageable) {
        QPost post = QPost.post;
        QPostCategory postCategory = QPostCategory.postCategory;
        QComment comment = QComment.comment;

        return jpaQueryFactory.selectFrom(post)
                .leftJoin(post.postCategoryList, postCategory)
                .leftJoin(post.commentList, comment)
                .fetchJoin()
                .where(postCategory.category.id.eq(categoryId))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .groupBy(post.id)
                .fetch();
    }

}
