package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.dto.common.UserRoleEnum;
import io.dkargo.bulletinboard.entity.base.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTime {
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
    private UserRoleEnum role;

    @Builder
    public User(String name, String email, String password) {
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

//    public User(User member) {
//        super(member.getUsername(), member.getPassword(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
//
//        log.info("SecurityUser member.username = {}", member.getUsername());
//        log.info("SecurityUser member.password = {}", member.getPassword());
//        log.info("SecurityUser member.role = {}", member.getRole().toString());
//
//        this.member = member;
//    }

    //admin 권한 부여
    //TODO : Admin 권한 부여 기능 개발 해야함.
    public void grantAdmin() {
        this.role = UserRoleEnum.ADMIN;
    }

}
