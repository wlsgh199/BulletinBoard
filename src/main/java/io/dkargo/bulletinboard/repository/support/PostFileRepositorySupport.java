package io.dkargo.bulletinboard.repository.support;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.dkargo.bulletinboard.entity.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostFileRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public PostFileRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(PostFile.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<PostFile> findAllByPostId(Long postId) {
        QPostFile postFile = QPostFile.postFile;

        return jpaQueryFactory.selectFrom(postFile)
                .where(postFile.post.id.eq(postId))
                .fetch();
    }

    public void deleteAllByPostId(Long postId) {
        QPostFile postFile = QPostFile.postFile;

        jpaQueryFactory.delete(postFile)
                .where(postFile.post.id.eq(postId))
                .execute();
    }
}
