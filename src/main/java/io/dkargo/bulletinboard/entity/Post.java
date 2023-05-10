package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.dto.common.BooleanToYNConverter;
import io.dkargo.bulletinboard.dto.request.post.ReqUpdatePostDTO;
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
public class Post extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "title", nullable = false, length = 400)
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "longtext")
    private String content;

    @Column(name = "post_password", length = 20)
    private String postPassword;

    @Column(name = "post_open_use_flag_Yn", nullable = false, length = 1)
    @Convert(converter = BooleanToYNConverter.class)
    private Boolean postOpenUseFlag;

    @Column(name = "reply_comment_use_flag_Yn", nullable = false, length = 1)
    @Convert(converter = BooleanToYNConverter.class)
    private Boolean replyCommentUseFlag;

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

    @Builder
    public Post(User user, String title, String content, boolean postOpenUseFlag,
                String postPassword, boolean replyCommentUseFlag) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.postOpenUseFlag = postOpenUseFlag;
        this.postPassword = postPassword;
        this.replyCommentUseFlag = replyCommentUseFlag;
    }

    public void update(ReqUpdatePostDTO reqUpdatePostDTO) {
        this.title = reqUpdatePostDTO.getTitle();
        this.content = reqUpdatePostDTO.getContent();
        this.postOpenUseFlag = reqUpdatePostDTO.getPostOpenUseFlag();
        this.postPassword = reqUpdatePostDTO.getPostPassword();
        this.replyCommentUseFlag = reqUpdatePostDTO.getReplyCommentUseFlag();
    }

    //조회수 증가
    public void incrementClickCount() {
        this.clickCount += 1;
    }

    //TODO : password 체크
    public boolean passwordValidCheck(String postPassword) {
        return this.postPassword.equals(postPassword);
    }

}
