package com.aps.quality.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditInfoDto implements Serializable {
    @ApiModelProperty(name = "creditId", required = false, example = "", notes = "ID")
    private Integer creditId;
    @ApiModelProperty(name = "userID", required = false, example = "", notes = "学生ID")
    private Integer userID;
    @ApiModelProperty(name = "campaignType", required = false, example = "", notes = "项目类型")
    private String campaignType;
    @ApiModelProperty(name = "campaignName", required = false, example = "", notes = "项目名称")
    private String campaignName;
    @ApiModelProperty(name = "credit", required = false, example = "", notes = "学分")
    private BigDecimal credit;
    @ApiModelProperty(name = "creditYear", required = false, example = "", notes = "学分获取年份")
    private Integer creditYear;
    @ApiModelProperty(name = "creditMonth", required = false, example = "", notes = "学分获取月份")
    private Integer creditMonth;
    @ApiModelProperty(name = "creditTime", required = false, example = "", notes = "学分获取时间")
    private Date creditTime;
    @ApiModelProperty(name = "instructor", required = false, example = "", notes = "指导老师")
    private String instructor;
    @ApiModelProperty(name = "atr1", required = false, example = "", notes = "退回原因")
    private String atr1;
    @ApiModelProperty(name = "atr2", required = false, example = "", notes = "备用")
    private String atr2;
    @ApiModelProperty(name = "atr3", required = false, example = "", notes = "备用")
    private String atr3;
    @ApiModelProperty(name = "atr4", required = false, example = "", notes = "备用")
    private String atr4;
    @ApiModelProperty(name = "atr5", required = false, example = "", notes = "备用")
    private Integer atr5;
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
