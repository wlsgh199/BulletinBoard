package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.dto.request.reply.ReqUpdateReplyDTO;
import io.dkargo.bulletinboard.entity.base.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "comment_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @Column(name = "content", nullable = false, length = 3000, columnDefinition = "text")
    private String content;

    @Builder
    public Reply(Comment comment, Member member, String content) {
        this.comment = comment;
        this.member = member;
        this.content = content;
    }

    public void update(ReqUpdateReplyDTO reqUpdateReplyDTO) {
        this.content = reqUpdateReplyDTO.getContent();
    }
}
