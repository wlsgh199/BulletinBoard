package io.dkargo.bulletinboard.entity.base;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class BaseTime {

    @CreationTimestamp
    @Column(name = "create_time")
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "update_time")
    private LocalDateTime updateDate;
}