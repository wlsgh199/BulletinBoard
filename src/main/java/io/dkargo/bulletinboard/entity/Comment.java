package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.entity.base.BaseTime;
import lombok.AccessLevel;
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

    @JoinColumn(name = "post")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @JoinColumn(name = "member")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "depth")
    private Integer depth;

    @OneToMany(
            mappedBy = "comment",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Reply> replyList = new ArrayList<>();

    public Comment(Post post, Member member, String content, Integer depth) {
        this.post = post;
        this.member = member;
        this.content = content;
        this.depth = depth;
    }
}
