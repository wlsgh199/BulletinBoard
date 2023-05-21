package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.converter.BooleanToYNConverter;
import io.dkargo.bulletinboard.dto.request.post.ReqUpdatePostDTO;
import io.dkargo.bulletinboard.entity.base.BaseTime;
import io.dkargo.bulletinboard.exception.CustomException;
import io.dkargo.bulletinboard.exception.ErrorCodeEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
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

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

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

    @Column(name = "click_count", columnDefinition = "bigint(20) default 0" , nullable = false)
    private Long clickCount;

    @OneToMany(mappedBy = "post")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<PostCategory> postCategoryList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<PostFile> postFileList = new ArrayList<>();


    @OneToMany(mappedBy = "post")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public Post(Member member, String title, String content, boolean postOpenUseFlag,
                String postPassword, boolean replyCommentUseFlag) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.postOpenUseFlag = postOpenUseFlag;
        this.postPassword = postPassword;
        this.replyCommentUseFlag = replyCommentUseFlag;
        this.clickCount = 0L;
    }

    public void update(ReqUpdatePostDTO reqUpdatePostDTO) {
        this.title = reqUpdatePostDTO.getTitle();
        this.content = reqUpdatePostDTO.getContent();
        this.postOpenUseFlag = reqUpdatePostDTO.getPostOpenUseFlag();
        this.postPassword = reqUpdatePostDTO.getPostPassword();
        this.replyCommentUseFlag = reqUpdatePostDTO.getReplyCommentUseFlag();
    }

    //패스워드 blank 체크
    public boolean passwordValidCheck(String password) {
        return !StringUtils.isBlank(password);
    }

    //게시판 비밀번호 확인
    public boolean passwordCheck(String password) {
        return StringUtils.equals(this.postPassword, password);
    }

}
