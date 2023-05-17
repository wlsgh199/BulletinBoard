//package io.dkargo.bulletinboard.repository.support;
//
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import io.dkargo.bulletinboard.entity.*;
//import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//import static io.dkargo.bulletinboard.entity.QPostFile.postFile;
//
//@Repository
//public class PostFileRepositorySupport extends QuerydslRepositorySupport {
//    private final JPAQueryFactory jpaQueryFactory;
//
//    public PostFileRepositorySupport(JPAQueryFactory jpaQueryFactory) {
//        super(PostFile.class);
//        this.jpaQueryFactory = jpaQueryFactory;
//    }
//
////    //파일 리스트 조회
////    public List<PostFile> findAllByPostId(long postId) {
////        return jpaQueryFactory.selectFrom(postFile)
////                .where(postFile.post.id.eq(postId))
////                .fetch();
////    }
////
////    //게시물 파일 리스트 삭제
////    public void deleteAllByPostId(long postId) {
////        jpaQueryFactory.delete(postFile)
////                .where(postFile.post.id.eq(postId))
////                .execute();
////    }
//}
