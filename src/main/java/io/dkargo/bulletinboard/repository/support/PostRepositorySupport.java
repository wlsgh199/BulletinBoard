package io.dkargo.bulletinboard.repository.support;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.entity.QPost;
import io.dkargo.bulletinboard.entity.QPostCategory;
import io.dkargo.bulletinboard.entity.QPostFile;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public PostRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Post.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Post> findAllPost(Pageable pageable) {
        QPost post = QPost.post;
        QPostFile postFile = QPostFile.postFile;

        return jpaQueryFactory.selectFrom(post)
                .distinct()
                .leftJoin(post.postFileList, postFile).fetchJoin()
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<Post> findPostById(Long id) {
        QPost post = QPost.post;
        QPostFile postFile = QPostFile.postFile;

        return jpaQueryFactory.selectFrom(post)
                .distinct()
                .leftJoin(post.postFileList, postFile).fetchJoin()
                .where(post.id.eq(id))
                .fetch();
    }

    public List<Post> findPostByMemberId(Long memberId, Pageable pageable) {
        QPost post = QPost.post;
        QPostFile postFile = QPostFile.postFile;

        return jpaQueryFactory.selectFrom(post)
                .distinct()
                .leftJoin(post.postFileList, postFile).fetchJoin()
                .where(post.member.id.eq(memberId))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<Post> findPostByTitle(String title, Pageable pageable) {
        QPost post = QPost.post;
        QPostFile postFile = QPostFile.postFile;

        return jpaQueryFactory.selectFrom(post)
                .distinct()
                .leftJoin(post.postFileList, postFile).fetchJoin()
                .where(post.title.contains(title))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<Post> findPostByContent(String title, Pageable pageable) {
        QPost post = QPost.post;
        QPostFile postFile = QPostFile.postFile;

        return jpaQueryFactory.selectFrom(post)
                .distinct()
                .leftJoin(post.postFileList, postFile).fetchJoin()
                .where(post.title.contains(title))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<Post> findPostByCategory(Long categoryId, Pageable pageable) {
        QPost post = QPost.post;
        QPostFile postFile = QPostFile.postFile;
        QPostCategory postCategory = QPostCategory.postCategory;

        return jpaQueryFactory.selectFrom(post)
                .distinct()
                .leftJoin(post.postFileList, postFile)
                .leftJoin(post.postCategoryList, postCategory).fetchJoin()
                .where(postCategory.category.id.eq(categoryId))
                .orderBy(post.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

}
