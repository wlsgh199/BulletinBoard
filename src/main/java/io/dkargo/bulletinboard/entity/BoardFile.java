package io.dkargo.bulletinboard.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class BoardFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post postId;

    @Column(nullable = false, name = "file_name")
    private String fileName;

    @Column(nullable = false, name = "file_path")
    private String fullPath;

    @Column(nullable = false, name = "file_size")
    private Long fileSize;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
