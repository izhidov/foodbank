package com.inzami.fp.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.ZonedDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity implements Identifiable{

    private static final long serialVersionUID = 1L;

    @CreatedDate
    @Column(name = "created_at")
    private ZonedDateTime createdAt = ZonedDateTime.now();
    @LastModifiedDate
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt = ZonedDateTime.now();
}
