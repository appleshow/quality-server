package com.aps.quality.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserOrganizationConciseDto implements Serializable {
    @ApiModelProperty(name = "userOrganizationId", required = false, example = "", notes = "ID")
    private Integer userOrganizationId;
    @ApiModelProperty(name = "userId", required = false, example = "", notes = "用户ID")
    private Integer userId;
    @ApiModelProperty(name = "organizationId", required = false, example = "", notes = "组织ID")
    private Integer organizationId;
    @ApiModelProperty(name = "organizationConcise", required = false, example = "", notes = "组织信息")
    private OrganizationConciseDto organizationConcise;
}
