package io.dkargo.bulletinboard.repository.support;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.dkargo.bulletinboard.entity.Comment;
import io.dkargo.bulletinboard.entity.User;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static io.dkargo.bulletinboard.entity.QUser.user;

@Repository
public class UserRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public UserRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(User.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    //유저 이메일로 조회
    public User findUserByUserMail(String mail) {
        return jpaQueryFactory.selectFrom(user)
                .where(user.userEmail.eq(mail))
                .fetchOne();
    }
}
