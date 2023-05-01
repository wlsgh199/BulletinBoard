package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.dto.request.ReqPostDTO;
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
public class Post extends BaseTime{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "member")
    @ManyToOne( fetch = FetchType.LAZY)
    public Member member;

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

    @Column(name = "click_count")
    private Long clickCount = 0L;

    @OneToMany(mappedBy = "post")
    private List<PostCategory> postCategoryList = new ArrayList<>();

    @OneToMany(
            mappedBy = "post",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<PostFile> postFileList = new ArrayList<>();


    @OneToMany(
            mappedBy = "post",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Comment> commentList = new ArrayList<>();

    public Post(Member member, ReqPostDTO reqPostDTO) {
        this.member =  member;
        this.title = reqPostDTO.getTitle();
        this.content = reqPostDTO.getContent();
        this.postOpenUseFlag = reqPostDTO.getPostOpenUseFlag();
        this.postPassword = reqPostDTO.getPostPassword();
        this.replyCommentUseFlag = reqPostDTO.getReplyCommentUseFlag();
    }


}
