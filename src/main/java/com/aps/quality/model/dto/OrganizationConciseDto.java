package com.aps.quality.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrganizationConciseDto implements Serializable {
    @ApiModelProperty(name = "organizationId", required = false, example = "", notes = "组织ID")
    private Integer organizationId;
    @ApiModelProperty(name = "organizationName", required = false, example = "", notes = "组织名称")
    private String organizationName;
    @ApiModelProperty(name = "organizationType", required = false, example = "", notes = "组织类型")
    private String organizationType;
    @ApiModelProperty(name = "organizationLevel", required = false, example = "", notes = "组织屋级")
    private Integer organizationLevel;
    @ApiModelProperty(name = "fatherOrganizationId", required = false, example = "", notes = "父组织ID")
    private Integer fatherOrganizationId;
}
