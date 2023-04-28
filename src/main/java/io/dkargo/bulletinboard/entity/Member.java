package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.dto.request.ReqMemberDTO;
import io.dkargo.bulletinboard.entity.base.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

//    @Column(name = "create_time")
//    @CreatedDate
////    @CreationTimestamp
//    private LocalDateTime createTime;
//
//    @Column(name = "update_time")
////    @UpdateTimestamp
//    @LastModifiedDate
//    private LocalDateTime updateTime;

    public Member(ReqMemberDTO reqMemberDTO) {
        this.userName = reqMemberDTO.getUserName();
    }



}
