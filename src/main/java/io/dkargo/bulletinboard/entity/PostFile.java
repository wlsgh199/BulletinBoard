package io.dkargo.bulletinboard.entity;

import io.dkargo.bulletinboard.entity.base.BaseTime;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostFile extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "post")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @Column(nullable = false, name = "file_name")
    private String fileName;

    @Column(nullable = false, name = "file_path")
    private String filePath;

    @Column(nullable = false, name = "file_size")
    private Long fileSize;

    @Column(name = "content_type")
    private String contentType;

    @Builder
    public PostFile(Post post, String fileName, String filePath, Long fileSize, String contentType ) {
        this.post = post;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.contentType = contentType;
    }
}
