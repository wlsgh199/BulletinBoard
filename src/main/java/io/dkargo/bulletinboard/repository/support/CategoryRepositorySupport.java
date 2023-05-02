package io.dkargo.bulletinboard.repository.support;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.dkargo.bulletinboard.entity.*;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepositorySupport extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public CategoryRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Category.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Category> findAllCategory() {
        QCategory category = QCategory.category;

        return jpaQueryFactory.selectFrom(category)
                .orderBy(category.depth.asc())
                .fetch();
    }
}
