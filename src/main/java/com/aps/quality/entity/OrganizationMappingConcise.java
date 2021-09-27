package com.aps.quality.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "organization_mapping_info")
public class OrganizationMappingConcise implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "organization_mapping_id", nullable = false)
    private Integer organizationMappingId;
    @Column(name = "father_organization_id", nullable = false)
    private Integer fatherOrganizationId;
    @Column(name = "child_organization_id", nullable = false)
    private Integer childOrganizationId;
}
