package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.entity.base.BaseTime;
import lombok.AccessLevel;
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

    @JoinColumn(name = "member")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @JoinColumn(name = "comment")
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "depth")
    private Integer depth;

    public Reply(Comment comment, Member member , String content, Integer depth) {
        this.comment = comment;
        this.member = member;
        this.content = content;
        this.depth = depth;

    }
}
