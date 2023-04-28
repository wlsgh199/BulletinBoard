package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.dto.request.ReqCategoryDTO;
import io.dkargo.bulletinboard.entity.base.BaseTime;
import lombok.AccessLevel;
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

    @Column(name ="parent_id")
    private Long parentId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "depth")
    private Integer depth;

    @OneToMany(mappedBy = "category")
    private List<PostCategory> postCategoryList = new ArrayList<>();

    public Category(ReqCategoryDTO reqCategoryDTO) {
        this.parentId = reqCategoryDTO.getParentId();
        this.categoryName = reqCategoryDTO.getCategoryName();
        this.depth = reqCategoryDTO.getDepth();
    }

}
