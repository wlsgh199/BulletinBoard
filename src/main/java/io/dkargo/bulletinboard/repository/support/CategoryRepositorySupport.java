package io.dkargo.bulletinboard.repository.support;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.dkargo.bulletinboard.entity.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static io.dkargo.bulletinboard.entity.QCategory.category;

@Repository
public class CategoryRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public CategoryRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Category.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    //전체 카테고리 조회
    public List<Category> findAllCategory() {
        return jpaQueryFactory.selectFrom(category)
                .orderBy(category.parentId.asc())
                .fetch();
    }
}
