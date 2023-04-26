package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.dto.request.ReqCategoryDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name ="parent_id")
    private Integer parentId;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "depth")
    private Integer depth;

    @OneToMany(mappedBy = "categoryId")
    private List<PostCategory> postCategoryList = new ArrayList<>();

    public Category() {

    }
    public Category(ReqCategoryDTO reqCategoryDTO) {
        this.parentId = reqCategoryDTO.getParentId();
        this.categoryName = reqCategoryDTO.getCategoryName();
        this.depth = reqCategoryDTO.getDepth();
    }

}
