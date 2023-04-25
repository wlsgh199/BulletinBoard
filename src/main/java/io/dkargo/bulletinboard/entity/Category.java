package io.dkargo.bulletinboard.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name ="parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category parentId;

    @JoinColumn(name ="child_id")
    @OneToMany(fetch = FetchType.LAZY)
    private List<Category> childId;

    @Column(name = "use_flag_Yn")
    private String useFlagYn;
}
