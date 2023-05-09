package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.dto.request.category.ReqPatchCategoryDTO;
import io.dkargo.bulletinboard.dto.request.category.ReqPutCategoryDTO;
import io.dkargo.bulletinboard.dto.request.post.ReqPutPostDTO;
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
public class Category extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "parent_id", referencedColumnName = "id", insertable = false, updatable = false)
//    private Category parent;
//    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
//    private List<Category> children = new ArrayList<>();

    //TODO : 유니크 처리
    @Column(name = "category_name", nullable = false, length = 20)
    private String categoryName;

    @Column(name = "depth", nullable = false)
    private Integer depth;

    @OneToMany(mappedBy = "category")
    private List<PostCategory> postCategoryList = new ArrayList<>();

    @Builder
    public Category(Long parentId, String categoryName, Integer depth) {
        this.parentId = parentId;
        this.categoryName = categoryName;
        this.depth = depth;
    }

    public void patch(ReqPatchCategoryDTO reqPatchCategoryDTO) {
        this.parentId = reqPatchCategoryDTO.getParentId();
        this.categoryName = reqPatchCategoryDTO.getCategoryName();
        this.depth = reqPatchCategoryDTO.getDepth();
    }

    public void put(ReqPutCategoryDTO reqPutCategoryDTO) {
        this.parentId = reqPutCategoryDTO.getParentId();
        this.categoryName = reqPutCategoryDTO.getCategoryName();
        this.depth = reqPutCategoryDTO.getDepth();
    }

}
