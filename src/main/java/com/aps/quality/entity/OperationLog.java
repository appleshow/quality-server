package com.aps.quality.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "operation_log")
@EntityListeners(AuditingEntityListener.class)
public class OperationLog implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "category", nullable = true, length = 60)
    private String category;

    @Column(name = "subcategory", nullable = true, length = 60)
    private String subcategory;

    @Column(name = "operation_type", nullable = true, length = 60)
    private String operationType;

    @Column(name = "operation_subtype", nullable = true, length = 60)
    private String operationSubtype;

    @Column(name = "operation_related_id", nullable = true)
    private Integer operationRelatedId;

    @Column(name = "operation_related_guid", nullable = true, length = 64)
    private String operationRelatedGuid;

    @Column(name = "operation_data", nullable = true, length = -1)
    private String operationData;

    @Column(name = "error_message", nullable = true, length = -1)
    private String errorMessage;

    @Column(name = "atr1", nullable = true, length = 100)
    private String atr1;

    @Column(name = "atr2", nullable = true, length = 100)
    private String atr2;

    @Column(name = "atr3", nullable = true, length = 100)
    private String atr3;

    @Column(name = "atr4", nullable = true, length = 100)
    private String atr4;

    @Column(name = "atr5", nullable = true, length = 100)
    private String atr5;

    @Column(name = "status", nullable = true)
    private Integer status;

    @Column(name = "flag", nullable = true, length = 2)
    private String flag;

    @Column(name = "remark", nullable = true, length = 400)
    private String remark;

    @Column(name = "create_time", updatable = false)
    @CreatedDate
    private Date createTime;

    @Column(name = "create_by", updatable = false, length = 20)
    @CreatedBy
    private String createBy;

    @Column(name = "update_time", nullable = true)
    @LastModifiedDate
    private Date updateTime;

    @Column(name = "update_by", nullable = true, length = 20)
    @LastModifiedBy
    private String updateBy;
}
