package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.dto.request.reply.ReqUpdateReplyDTO;
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

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "comment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    // TODO :@OnDelete 검토
    private Comment comment;

    @Column(name = "content", nullable = false, length = 3000, columnDefinition = "text")
    private String content;

    @Builder
    public Reply(Comment comment, User user, String content) {
        this.comment = comment;
        this.user = user;
        this.content = content;
    }

    public void update(ReqUpdateReplyDTO reqUpdateReplyDTO) {
        this.content = reqUpdateReplyDTO.getContent();
    }
}
