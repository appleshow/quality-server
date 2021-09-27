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
@Table(name = "user_organization_info")
public class UserOrganizationConcise implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "user_organization_id", nullable = false)
    private Integer userOrganizationId;
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Column(name = "organization_id", nullable = false)
    private Integer organizationId;

    @OneToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "organization_id", insertable = false, updatable = false)
    @LazyCollection(LazyCollectionOption.FALSE)
    @NotFound(action = NotFoundAction.IGNORE)
    private OrganizationConcise organizationInfo;
}
