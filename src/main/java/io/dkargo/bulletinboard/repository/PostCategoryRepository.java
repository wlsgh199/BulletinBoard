package io.dkargo.bulletinboard.repository;

import io.dkargo.bulletinboard.entity.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCategoryRepository extends JpaRepository<PostCategory,Long> {

}
