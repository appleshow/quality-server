package com.aps.quality.entity;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "credit_info")
@EntityListeners(AuditingEntityListener.class)
public class CreditInfo implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "credit_id", nullable = false)
    private Integer creditId;
    @Column(name = "user_id", nullable = false, length = 100)
    private Integer userId;
    @Column(name = "campaign_type", nullable = true, length = 100)
    private String campaignType;
    @Column(name = "campaign_name", nullable = true, length = 200)
    private String campaignName;
    @Column(name = "credit", nullable = true, precision = 3)
    private BigDecimal credit;
    @Column(name = "credit_year", nullable = true, length = 5)
    private Integer creditYear;
    @Column(name = "credit_month", nullable = true, length = 5)
    private Integer creditMonth;
    @Column(name = "credit_time", nullable = true)
    private Date creditTime;
    @Column(name = "instructor", nullable = true)
    private String instructor;
    @Column(name = "atr1", nullable = true, length = 1000)
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

    @OneToMany
    @JoinColumn(name = "credit_id", referencedColumnName = "credit_id", insertable = false, updatable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<CreditApprovalConcise> creditApprovalConcises;

    public void beforeSave() {
        creditApprovalConcises = null;
    }
}
