package io.dkargo.bulletinboard.repository;

import io.dkargo.bulletinboard.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    List<Category> findCategoriesByParentIdOrderByCategoryNameAsc(Integer parentId);
}
