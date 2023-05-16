package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.dto.request.category.ReqUpdateCategoryNameDTO;
import io.dkargo.bulletinboard.entity.base.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "categoryConstraint",
                        columnNames = {"parent_id", "category_name"}
                )
        }
)
public class Category extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "parent_id")
    private Integer parentId;

    @Column(name = "category_name", nullable = false, length = 20, unique = true)
    private String categoryName;

    @OneToMany(
            mappedBy = "parentId",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Category> childList = new ArrayList<>();

    @Builder
    public Category(Integer parentId, String categoryName) {
        this.parentId = parentId;
        this.categoryName = categoryName;
    }

    public void update(ReqUpdateCategoryNameDTO reqUpdateCategoryNameDTO) {
        this.categoryName = reqUpdateCategoryNameDTO.getCategoryName();
    }
}
