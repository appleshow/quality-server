package com.aps.quality.model.authority;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RemoveRoleFromUserRequest implements Serializable {
    @ApiModelProperty(name = "roleId", required = false, example = "", notes = "权限ID")
    private Integer roleId;
    @ApiModelProperty(name = "userId", required = false, example = "", notes = "用户ID")
    private Integer userId;
}
