package com.aps.quality.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "organization_info")
public class OrganizationConcise implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "organization_id", nullable = false)
    private Integer organizationId;
    @Column(name = "organization_name", nullable = true, length = 60)
    private String organizationName;
    @Column(name = "organization_type", nullable = true, length = 2)
    private String organizationType;
    @Column(name = "organization_level", nullable = true)
    private Integer organizationLevel;
    @Column(name = "father_organization_id", nullable = true)
    private Integer fatherOrganizationId;
}
