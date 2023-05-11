package io.dkargo.bulletinboard.repository;

import io.dkargo.bulletinboard.entity.Comment;
import io.dkargo.bulletinboard.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply,Long> {
    Integer countReplyByComment(Comment comment);
}
