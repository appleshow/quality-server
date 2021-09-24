package com.aps.quality.model.authority;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CreateGroupRequest implements Serializable {
    @ApiModelProperty(name = "groupName", required = false, example = "", notes = "权限组名称")
    private String groupName;
    @ApiModelProperty(name = "status", required = true, example = "1", notes = "状态。0 无效（取消）， 1 有效")
    private String remark;
}
