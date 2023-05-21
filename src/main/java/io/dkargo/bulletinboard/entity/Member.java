package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.dto.common.UserRoleEnum;
import io.dkargo.bulletinboard.dto.request.member.ReqUpdateMemberDTO;
import io.dkargo.bulletinboard.entity.base.BaseTime;
import io.dkargo.bulletinboard.exception.CustomException;
import io.dkargo.bulletinboard.exception.ErrorCodeEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false, length = 20)
    private UserRoleEnum role;

    @Builder
    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = UserRoleEnum.USER;
    }

    //유저 아이디 체크
    public boolean userIdValidCheck(Long userId) {
        return this.id.equals(userId);
    }

    //비밀번호 암호화
    public void encryptPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //비밀번호 확인
    public boolean passwordCheck(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.matches(password, this.password);
    }

    public void update(ReqUpdateMemberDTO reqUpdateMemberDTO) {
        this.name = reqUpdateMemberDTO.getName();
    }

    //admin 권한 부여
    public void grantAdmin() {
        this.role = UserRoleEnum.ADMIN;
    }

    public boolean isUser() {
        return this.role.equals(UserRoleEnum.USER);
    }

}
