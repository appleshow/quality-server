package com.aps.quality.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleInfoDto implements Serializable {
    @ApiModelProperty(name = "roleId", required = false, example = "", notes = "权限ID")
    private Integer roleId;
    @ApiModelProperty(name = "roleCode", required = false, example = "", notes = "权限代码")
    private String roleCode;
    @ApiModelProperty(name = "roleName", required = false, example = "", notes = "权限名称")
    private String roleName;
    @ApiModelProperty(name = "roleType", required = false, example = "", notes = "权限类型")
    private String roleType;
    @ApiModelProperty(name = "fatherRoleId", required = false, example = "", notes = "父权限ID")
    private Integer fatherRoleId;
    @ApiModelProperty(name = "atr1", required = false, example = "", notes = "备用")
    private String atr1;
    @ApiModelProperty(name = "atr2", required = false, example = "", notes = "备用")
    private String atr2;
    @ApiModelProperty(name = "atr3", required = false, example = "", notes = "备用")
    private String atr3;
    @ApiModelProperty(name = "atr4", required = false, example = "", notes = "备用")
    private String atr4;
    @ApiModelProperty(name = "atr5", required = false, example = "", notes = "备用")
    private String atr5;
    @ApiModelProperty(name = "flag", required = true, example = "Y", notes = "标志")
    private String flag;
    @ApiModelProperty(name = "status", required = true, example = "1", notes = "状态。0 无效（取消）， 1 有效")
    private Integer status;
    @ApiModelProperty(name = "remark", required = false, example = "", notes = "备注")
    private String remark;
    @ApiModelProperty(name = "version", required = false, example = "1", notes = "数据版本号。创建无须此参数，更新必须携带此参数")
    private Integer version;
    @ApiModelProperty(name = "createTime", required = true, example = "2020-12-25T06:55:14.000+0000", notes = "创建时间(UTC)")
    private Date createTime;
    @ApiModelProperty(name = "createBy", required = true, example = "test", notes = "创建人")
    private String createBy;
    @ApiModelProperty(name = "updateTime", required = true, example = "2020-12-25T06:55:14.000+0000", notes = "最后更新时间(UTC)")
    private Date updateTime;
    @ApiModelProperty(name = "updateBy", required = true, example = "test", notes = "最后更新人")
    private String updateBy;
}
