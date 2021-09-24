package com.aps.quality.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "metadata")
@EntityListeners(AuditingEntityListener.class)
public class Metadata implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "metadata_id", nullable = false)
    private Integer metadataId;
    @Column(name = "type", nullable = true, length = 60)
    private String type;
    @Column(name = "subtype", nullable = true, length = 60)
    private String subtype;
    @Column(name = "category", nullable = true, length = 60)
    private String category;
    @Column(name = "subcategory", nullable = true, length = 60)
    private String subcategory;
    @Column(name = "code", nullable = true, length = 60)
    private String code;
    @Column(name = "subcode", nullable = true, length = 60)
    private String subcode;
    @Column(name = "name", nullable = true, length = 200)
    private String name;
    @Column(name = "int_value", nullable = true)
    private Integer intValue;
    @Column(name = "number_value", nullable = true, precision = 3)
    private BigDecimal numberValue;
    @Column(name = "str_value", nullable = true, length = 200)
    private String strValue;
    @Column(name = "date_value", nullable = true)
    private Date dateValue;
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
}
