package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.dto.common.BooleanToYNConverter;
import io.dkargo.bulletinboard.dto.request.comment.ReqUpdateCommentDTO;
import io.dkargo.bulletinboard.entity.base.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(name = "content", nullable = false, length = 3000, columnDefinition = "text")
    private String content;

    @Column(name = "reply_exist_Yn", nullable = false, length = 1)
    @Convert(converter = BooleanToYNConverter.class)
    private Boolean replyExistFlag = false;

    @OneToMany(
            mappedBy = "comment",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Reply> replyList = new ArrayList<>();

    @Builder
    public Comment(Post post, Member member, String content) {
        this.post = post;
        this.member = member;
        this.content = content;
    }

    public void update(ReqUpdateCommentDTO reqUpdateCommentDTO) {
        this.content = reqUpdateCommentDTO.getContent();
    }

    //댓글 달릴때, 없어질때 플래그 업데이트
    public void replyExistFlagUpdate(boolean flag) {
        this.replyExistFlag = flag;
    }

}
