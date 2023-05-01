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

@Repository
public class CommentRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public CommentRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Comment.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Comment> findCommentByPostId(Long postId) {
        QComment comment = QComment.comment;
        QReply reply = QReply.reply;

        List<Comment> commentList = jpaQueryFactory.selectFrom(comment)
                .distinct()
                .leftJoin(comment.replyList, reply)
                .fetchJoin()
                .where(comment.post.id.eq(postId))
                .fetch();

//        for(Comment comment1 : commentList) {
//            System.out.println("comment1.getContent() = " + comment1.getContent());
//            for(Reply reply1 :  comment1.getReplyList()) {
//                System.out.println("reply1.getComment() = " + reply1.getContent());
//                System.out.println("reply1.getDepth() = " + reply1.getDepth());
//            }
//        }
        return commentList;
    }

}
