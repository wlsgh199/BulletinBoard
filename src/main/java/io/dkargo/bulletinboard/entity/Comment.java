package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.entity.base.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "reply")
    @ManyToOne(fetch = FetchType.LAZY)
    private Reply reply;

    @JoinColumn(name = "user")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member user;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "depth")
    private Integer depth;
}
