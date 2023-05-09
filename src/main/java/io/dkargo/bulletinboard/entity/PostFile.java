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

    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @Column(name = "file_name", nullable = false, length = 100)
    private String fileName;

    @Column(name = "file_path", nullable = false, length = 100)
    private String filePath;

    @Column(name = "file_size", nullable = false)
    private Long fileSize;

    @Column(name = "content_type", nullable = false, length = 100)
    private String contentType;

    @Builder
    public PostFile(Post post, String fileName, String filePath, Long fileSize, String contentType) {
        this.post = post;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.contentType = contentType;
    }
}
