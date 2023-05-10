package io.dkargo.bulletinboard.entity.base;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseTime {

    @CreationTimestamp
    @Column(name = "create_time", nullable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateDate;
}