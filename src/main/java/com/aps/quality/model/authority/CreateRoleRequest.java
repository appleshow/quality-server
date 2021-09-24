package com.aps.quality.model.authority;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CreateRoleRequest implements Serializable {
    @ApiModelProperty(name = "roleName", required = false, example = "", notes = "权限名称")
    private String roleName;
    @ApiModelProperty(name = "fatherRoleId", required = false, example = "", notes = "父权限ID")
    private Integer fatherRoleId;
    @ApiModelProperty(name = "status", required = true, example = "1", notes = "状态。0 无效（取消）， 1 有效")
    private Integer status;
    @ApiModelProperty(name = "remark", required = false, example = "", notes = "备注")
    private String remark;
}
