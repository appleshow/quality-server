package com.aps.quality.model.authority;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateRoleRequest implements Serializable {
    @ApiModelProperty(name = "roleId", required = false, example = "", notes = "权限ID")
    private Integer roleId;
    @ApiModelProperty(name = "roleName", required = false, example = "", notes = "权限名称")
    private String roleName;
    @ApiModelProperty(name = "status", required = true, example = "1", notes = "状态。0 无效（取消）， 1 有效")
    private Integer status;
    @ApiModelProperty(name = "remark", required = false, example = "", notes = "备注")
    private String remark;
    @ApiModelProperty(name = "version", required = false, example = "1", notes = "数据版本号。创建无须此参数，更新必须携带此参数")
    private Integer version;
}
