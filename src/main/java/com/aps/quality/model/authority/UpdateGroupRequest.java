package com.aps.quality.model.authority;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateGroupRequest implements Serializable {
    @ApiModelProperty(name = "groupId", required = false, example = "", notes = "权限组ID")
    private Integer groupId;
    @ApiModelProperty(name = "groupName", required = false, example = "", notes = "权限组名称")
    private String groupName;
    @ApiModelProperty(name = "remark", required = false, example = "", notes = "备注")
    private String remark;
    @ApiModelProperty(name = "version", required = false, example = "1", notes = "数据版本号。创建无须此参数，更新必须携带此参数")
    private Integer version;
}
