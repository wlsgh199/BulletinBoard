package io.dkargo.bulletinboard.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    public Member userId;

    private String title;

    @Lob
    private String content;

    private String category1;

    private String category2;

    private String category3;

    @Column(name = "post_password")
    private String postPassword;

    @Column(name = "post_open_use_flag_Yn")
    private String postOpenUseFlag;

    @Column(name = "reply_comment_use_flag_Yn")
    private String replyCommentUseFlag;

    @Column(name = "click_count")
    private Long clickCount;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
