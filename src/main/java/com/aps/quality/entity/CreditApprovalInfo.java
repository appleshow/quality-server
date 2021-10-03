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
@Table(name = "credit_approval_info")
@EntityListeners(AuditingEntityListener.class)
public class CreditApprovalInfo implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "credit_approval_id", nullable = false)
    private Integer creditApprovalId;
    @Column(name = "credit_id", nullable = false)
    private Integer creditId;
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Column(name = "type", nullable = true, length = 100)
    private Integer type;
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

    @Version
    private Integer version;

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

    public void beforeSave() {}
}
