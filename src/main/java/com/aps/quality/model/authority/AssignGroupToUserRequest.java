package com.aps.quality.model.authority;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AssignGroupToUserRequest implements Serializable {
    @ApiModelProperty(name = "groupId", required = false, example = "", notes = "权限组ID")
    private Integer groupId;
    @ApiModelProperty(name = "userId", required = false, example = "", notes = "用户ID")
    private Integer userId;
}
