package io.dkargo.bulletinboard.repository;

import io.dkargo.bulletinboard.entity.Post;
import io.dkargo.bulletinboard.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostCategoryRepository extends JpaRepository<PostCategory,Long> {
    PostCategory findTopByPostOrderByCategoryDesc(Post post);

    PostCategory findTopByCategory_Id(long categoryId);
}
