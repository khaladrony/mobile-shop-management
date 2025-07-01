package com.rony.erpsoft.application_common.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long organizationId;
    
    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private Long createdBy;
    
    @CreatedDate
    @Column(name = "create_on", updatable = false)
    private LocalDateTime createOn;
    
    @LastModifiedBy
    @Column(name="updated_by")
    private Long updatedBy;
    
    @LastModifiedDate
    @Column(name="updated_on")
    private LocalDateTime updatedOn;
}