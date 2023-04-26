package io.dkargo.bulletinboard.repository;

import io.dkargo.bulletinboard.entity.Category;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Override
    <S extends Category> S save(S entity);

    @Override
    List<Category> findAll();
}
