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
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "group_info")
@EntityListeners(AuditingEntityListener.class)
public class GroupInfo implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "group_id", nullable = false)
    private Integer groupId;
    @Column(name = "group_name", nullable = false)
    private String groupName;
    @Column(name = "group_type", nullable = false)
    private String groupType;
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

    @OneToMany
    @JoinColumn(name = "group_id", referencedColumnName = "group_id", insertable = false, updatable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotFound(action = NotFoundAction.IGNORE)
    private List<GroupRoleInfo> groupRoleInfos;
}