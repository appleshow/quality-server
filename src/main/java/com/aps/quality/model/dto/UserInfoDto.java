package com.aps.quality.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoDto implements Serializable {
    @ApiModelProperty(name = "userId", required = true, example = "", notes = "用户ID")
    private Integer userId;
    @ApiModelProperty(name = "userCode", required = true, example = "", notes = "用户代码")
    private String userCode;
    @ApiModelProperty(name = "userName", required = true, example = "", notes = "用户名称")
    private String userName;
    @ApiModelProperty(name = "userGender", required = false, example = "", notes = "用户性别")
    private String userGender;
    @ApiModelProperty(name = "userPhone", required = false, example = "", notes = "用户手机号")
    private String userPhone;
    @ApiModelProperty(name = "userEmail", required = false, example = "", notes = "用户邮箱")
    private String userEmail;
    @ApiModelProperty(name = "userAddr", required = false, example = "", notes = "用户地址")
    private String userAddr;
    @ApiModelProperty(name = "userType", required = false, example = "", notes = "用户类型")
    private String userType;
    @ApiModelProperty(name = "organizationId", required = false, example = "", notes = "组织ID")
    private Integer organizationId;
    @ApiModelProperty(name = "teacherName", required = false, example = "", notes = "班主任老师")
    private String teacherName;
    @ApiModelProperty(name = "teacherPhone", required = false, example = "", notes = "班主任手机号")
    private String teacherPhone;
    @ApiModelProperty(name = "ssoId", required = false, example = "", notes = "SSO ID")
    private String ssoId;
    @ApiModelProperty(name = "ssoType", required = false, example = "", notes = "SSO类型")
    private String ssoType;
    @ApiModelProperty(name = "atr1", required = false, example = "", notes = "用户组织链路")
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
    @ApiModelProperty(name = "organizationInfo", required = true, example = "test", notes = "用户组织")
    private OrganizationConciseDto organizationInfo;
}
