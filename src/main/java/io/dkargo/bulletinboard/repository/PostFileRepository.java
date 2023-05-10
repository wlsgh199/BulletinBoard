package io.dkargo.bulletinboard.repository;

import io.dkargo.bulletinboard.entity.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostFileRepository extends JpaRepository<PostFile, Long> {
    List<PostFile> findPostFilesByPost(long postId);
}
