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
@Table(name = "user_info")
@EntityListeners(AuditingEntityListener.class)
public class UserInfo implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "user_code", nullable = false, length = 60)
    private String userCode;

    @Column(name = "user_name", nullable = true, length = 60)
    private String userName;

    @Column(name = "user_gender", nullable = true, length = 1)
    private String userGender;

    @Column(name = "user_phone", nullable = true, length = 20)
    private String userPhone;

    @Column(name = "user_email", nullable = true, length = 100)
    private String userEmail;

    @Column(name = "user_addr", nullable = true, length = 200)
    private String userAddr;

    @Column(name = "user_type", nullable = true, length = 2)
    private String userType;

    @Column(name = "user_password", nullable = true, length = 100)
    private String userPassword;

    @Column(name = "organization_id", nullable = false)
    private Integer organizationId;

    @Column(name = "teacher_name", nullable = false)
    private String teacherName;

    @Column(name = "teacher_phone", nullable = false)
    private String teacherPhone;

    @Column(name = "sso_id", nullable = true, updatable = false)
    private String ssoId;

    @Column(name = "sso_type", nullable = true, updatable = false)
    private String ssoType;

    @Column(name = "atr1", nullable = true, length = 400)
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

    @OneToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "organization_id", insertable = false, updatable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotFound(action = NotFoundAction.IGNORE)
    private OrganizationConcise organizationInfo;

    public void beforeSave() {
        organizationInfo = null;
    }
}
