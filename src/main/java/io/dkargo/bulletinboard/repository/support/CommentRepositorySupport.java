package io.dkargo.bulletinboard.repository.support;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.dkargo.bulletinboard.entity.Comment;
import io.dkargo.bulletinboard.entity.QComment;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class CommentRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public CommentRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Comment.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public void findCommentByPostId(Long postId) {
        QComment comment = QComment.comment;

//        jpaQueryFactory.selectFrom(comment)


    }

}
