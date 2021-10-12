package com.aps.quality.model.user;

import com.aps.quality.util.ErrorMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UpdateUserRequest implements Serializable {
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
    @ApiModelProperty(name = "atr1", required = false, example = "", notes = "用户组织链路")
    private String atr1;
    @ApiModelProperty(name = "flag", required = true, example = "Y", notes = "标志")
    private String flag;
    @ApiModelProperty(name = "status", required = true, example = "1", notes = "状态。0 无效（取消）， 1 有效")
    private Integer status;
    @ApiModelProperty(name = "remark", required = false, example = "", notes = "备注")
    private String remark;
    @ApiModelProperty(name = "version", required = false, example = "1", notes = "数据版本号。创建无须此参数，更新必须携带此参数")
    private Integer version;

    public ErrorMessage check() {
        if (null == userId) {
            return ErrorMessage.ID_CAN_NOT_BE_NULL;
        }
        if (!StringUtils.hasLength(userCode)) {
            return ErrorMessage.USER_CODE_INVALID;
        }
        if (null == version) {
            return ErrorMessage.VERSION_IS_NULL;
        }

        return ErrorMessage.NULL;
    }
}
