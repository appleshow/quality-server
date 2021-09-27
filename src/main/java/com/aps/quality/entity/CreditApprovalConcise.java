package com.aps.quality.entity;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "credit_approval_info")
public class CreditApprovalConcise implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "credit_approval_id", nullable = false)
    private Integer creditApprovalId;
    @Column(name = "credit_id", nullable = false)
    private Integer creditId;
    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotFound(action = NotFoundAction.IGNORE)
    private UserConcise userInfo;
}
