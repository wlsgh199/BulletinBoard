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
    @Column(name = "id")
    private Long id;

    @JoinColumn(name ="parent_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category parentId;

    @Column(name = "depth")
    private Integer depth;
}
