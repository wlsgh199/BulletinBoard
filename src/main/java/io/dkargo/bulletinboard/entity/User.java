package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.dto.common.UserRoleEnum;
import io.dkargo.bulletinboard.entity.base.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

//TODO : 복합 유니크 찾아보기 @UniqueConstranint?
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTime implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_email", nullable = false, unique = true)
    @Email
    private String userEmail;

    @Column(name = "user_name", nullable = false, length = 20)
    private String userName;

    @Column(name = "user_password", nullable = false)
    private String userPassword;

    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @Builder
    public User(String userName, String userMail, String userPassword) {
        this.userName = userName;
        this.userEmail = userMail;
        this.userPassword = userPassword;
        this.role = UserRoleEnum.USER;
    }

    //유저 아이디 체크
    public boolean userIdValidCheck(Long userId) {
        return this.id.equals(userId);
    }

    //비밀번호 암호화
    public void encryptPassword(PasswordEncoder passwordEncoder) {
        this.userPassword = passwordEncoder.encode(this.userPassword);
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(this.role.getValue()));
        return roles;
    }

    @Override
    public String getPassword() {
        return this.userPassword;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
