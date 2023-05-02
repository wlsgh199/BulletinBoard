package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.dto.request.ReqUserDTO;
import io.dkargo.bulletinboard.entity.base.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    public User(ReqUserDTO reqUserDTO) {
        this.userName = reqUserDTO.getUserName();
    }

    //유저 아이디 체크
    public boolean userIdValidCheck(Long userId) {
       return this.id.equals(userId);
    }

}
