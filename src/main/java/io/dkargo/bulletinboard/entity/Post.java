package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.dto.request.ReqPostDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne( fetch = FetchType.LAZY)
    public Member userId;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "post_password")
    private String postPassword;

    @Column(name = "post_open_use_flag_Yn")
    private String postOpenUseFlag;

    @Column(name = "reply_comment_use_flag_Yn")
    private String replyCommentUseFlag;

//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
//    private List<File> fileList = new ArrayList<>();

    @Column(name = "click_count")
    private Long clickCount = 0L;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "postId")
    private List<PostCategory> postCategoryList = new ArrayList<>();

    @OneToMany(
            mappedBy = "postId",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<File> files = new ArrayList<>();


    public Post(Member member, ReqPostDTO reqPostDTO) {
        this.userId =  member;
        this.title = reqPostDTO.getTitle();
        this.content = reqPostDTO.getContent();
        this.postOpenUseFlag = reqPostDTO.getPostOpenUseFlag();
        this.postPassword = reqPostDTO.getPostPassword();
        this.replyCommentUseFlag = reqPostDTO.getReplyCommentUseFlag();
//        this.fileList = reqPostDTO.getFileList();
    }


}
