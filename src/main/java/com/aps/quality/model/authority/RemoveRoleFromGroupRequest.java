package com.aps.quality.model.authority;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RemoveRoleFromGroupRequest implements Serializable {
    @ApiModelProperty(name = "roleId", required = false, example = "", notes = "权限ID")
    private Integer roleId;
    @ApiModelProperty(name = "groupId", required = false, example = "", notes = "权限组ID")
    private Integer groupId;
}
