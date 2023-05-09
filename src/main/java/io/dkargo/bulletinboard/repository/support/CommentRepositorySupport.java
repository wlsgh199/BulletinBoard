package io.dkargo.bulletinboard.repository.support;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.dkargo.bulletinboard.dto.response.ResCommentReplyDTO;
import io.dkargo.bulletinboard.entity.Comment;
import io.dkargo.bulletinboard.entity.QComment;
import io.dkargo.bulletinboard.entity.QReply;
import io.dkargo.bulletinboard.entity.Reply;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import static io.dkargo.bulletinboard.entity.QComment.comment;
import static io.dkargo.bulletinboard.entity.QReply.reply;

@Repository
public class CommentRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public CommentRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Comment.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    // 댓글 리스트 조회
    public List<Comment> findCommentByPostId(Long postId) {
        return jpaQueryFactory.selectFrom(comment)
                .distinct()
                .leftJoin(comment.replyList, reply)
                .fetchJoin()
                .where(comment.post.id.eq(postId))
                .orderBy(comment.post.id.asc())
                .fetch();
    }

}
