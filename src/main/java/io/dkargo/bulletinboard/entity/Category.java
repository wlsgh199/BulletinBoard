package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.dto.request.category.ReqPatchCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqPutCategoryDTO;
import io.dkargo.bulletinboard.entity.base.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "categoryConstraint",
                        columnNames= {"parent_id", "category_name"}
                )
        }
)
public class Category extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "category_name", nullable = false, length = 20)
    private String categoryName;

    @Builder
    public Category(Long parentId, String categoryName) {
        this.parentId = parentId;
        this.categoryName = categoryName;
    }

    public void patch(ReqPatchCategoryDTO reqPatchCategoryDTO) {
        this.parentId = reqPatchCategoryDTO.getParentId();
        this.categoryName = reqPatchCategoryDTO.getCategoryName();
    }

    public void put(ReqPutCategoryDTO reqPutCategoryDTO) {
        this.parentId = reqPutCategoryDTO.getParentId();
        this.categoryName = reqPutCategoryDTO.getCategoryName();
    }

}
