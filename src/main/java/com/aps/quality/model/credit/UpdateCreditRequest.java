package com.aps.quality.model.credit;

import com.aps.quality.util.ErrorMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class UpdateCreditRequest implements Serializable {
    @ApiModelProperty(name = "creditId", required = false, example = "", notes = "ID")
    private Integer creditId;
    @ApiModelProperty(name = "userId", required = false, example = "", notes = "学生ID")
    private Integer userId;
    @ApiModelProperty(name = "campaignType", required = false, example = "", notes = "项目类型")
    private String campaignType;
    @ApiModelProperty(name = "campaignName", required = false, example = "", notes = "项目名称")
    private String campaignName;
    @ApiModelProperty(name = "userCode", required = false, example = "", notes = "学号")
    private String userCode;
    @ApiModelProperty(name = "userName", required = false, example = "", notes = "学生姓名")
    private String userName;
    @ApiModelProperty(name = "userGender", required = false, example = "", notes = "学生性别")
    private String userGender;
    @ApiModelProperty(name = "userPhone", required = false, example = "", notes = "学生手机号")
    private String userPhone;
    @ApiModelProperty(name = "organizationId", required = false, example = "", notes = "学生系别")
    private Integer organizationId;
    @ApiModelProperty(name = "grade", required = false, example = "", notes = "学生班级")
    private String grade;
    @ApiModelProperty(name = "credit", required = false, example = "", notes = "学分")
    private BigDecimal credit;
    @ApiModelProperty(name = "creditTime", required = false, example = "", notes = "学分获取时间")
    private Date creditTime;
    @ApiModelProperty(name = "instructor", required = false, example = "", notes = "指导老师")
    private String instructor;
    @ApiModelProperty(name = "approvalType", required = false, example = "", notes = "审批类型")
    private String approvalType;
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
        if (null == creditId) {
            return ErrorMessage.ID_CAN_NOT_BE_NULL;
        }
        if (null == userId && !StringUtils.hasLength(userCode)) {
            return ErrorMessage.STUDENT_CODE_IS_NULL;
        }
        if (null == organizationId) {
            return ErrorMessage.ORGANIZATION_ID_INVALID;
        }
        if (null == version) {
            return ErrorMessage.VERSION_IS_NULL;
        }

        return ErrorMessage.NULL;
    }
}
