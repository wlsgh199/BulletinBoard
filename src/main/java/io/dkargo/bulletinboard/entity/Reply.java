package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.dto.request.comment.ReqPatchCommentDTO;
import io.dkargo.bulletinboard.dto.request.reply.ReqPatchReplyDTO;
import io.dkargo.bulletinboard.entity.base.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "user")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "comment")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @Lob
    @Column(name = "content")
    private String content;

    @Builder
    public Reply(Comment comment, User user , String content) {
        this.comment = comment;
        this.user = user;
        this.content = content;
    }

    public void patch(ReqPatchReplyDTO reqPatchReplyDTO) {
        this.content = reqPatchReplyDTO.getContent();
    }
}
