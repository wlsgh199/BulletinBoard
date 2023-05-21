package io.dkargo.bulletinboard.repository;

import io.dkargo.bulletinboard.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
//    @Modifying
//    @Query("update Post post set post.clickCount = post.clickCount + 1L where post.id = :id")
//    void incrementClickCount(@Param("id") long id);

}