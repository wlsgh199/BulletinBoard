package io.dkargo.bulletinboard.repository;

import io.dkargo.bulletinboard.entity.Member;
import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.entity.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


//    @Query("select p " +
//            " from Post p " +
//            " join fetch p.member m" +
//            " left join postFile" +
//            " where m.id = :memberId" )
//
////            + "where u in "
////            + "(select t from PostFile f inner join f.target t on f.source = :member) "
////            + "or u = :member ")
//    List<Post> findPostByMemberId(@Param("memberId") Long memberId);
}
