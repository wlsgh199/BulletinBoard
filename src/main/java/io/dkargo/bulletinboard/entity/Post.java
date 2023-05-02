package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.dto.request.post.ReqPatchPostDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqAddPostDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqPutPostDTO;
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

    @JoinColumn(name = "user")
    @ManyToOne( fetch = FetchType.LAZY)
    private User user;

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

    @OneToMany(
            mappedBy = "post",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
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

    public Post(User user, ReqAddPostDTO reqAddPostDTO) {
        this.user =  user;
        this.title = reqAddPostDTO.getTitle();
        this.content = reqAddPostDTO.getContent();
        this.postOpenUseFlag = reqAddPostDTO.getPostOpenUseFlag();
        this.postPassword = reqAddPostDTO.getPostPassword();
        this.replyCommentUseFlag = reqAddPostDTO.getReplyCommentUseFlag();
    }

    public void patch(ReqPatchPostDTO reqPatchPostDTO) {
        this.title = reqPatchPostDTO.getTitle();
        this.content = reqPatchPostDTO.getContent();
        this.postOpenUseFlag = reqPatchPostDTO.getPostOpenUseFlag();
        this.postPassword = reqPatchPostDTO.getPostPassword();
        this.replyCommentUseFlag = reqPatchPostDTO.getReplyCommentUseFlag();
    }

    public void update(ReqPutPostDTO reqPutPostDTO) {
        this.title = reqPutPostDTO.getTitle();
        this.content = reqPutPostDTO.getContent();
        this.postOpenUseFlag = reqPutPostDTO.getPostOpenUseFlag();
        this.postPassword = reqPutPostDTO.getPostPassword();
        this.replyCommentUseFlag = reqPutPostDTO.getReplyCommentUseFlag();
    }

    public boolean passwordValidCheck(String postPassword) {
        return this.postPassword.equals(postPassword);
    }

}
