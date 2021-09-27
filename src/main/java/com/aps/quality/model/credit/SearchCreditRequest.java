package com.aps.quality.model.credit;

import com.aps.quality.model.PageableSearch;
import com.aps.quality.util.DataUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class SearchCreditRequest extends PageableSearch {
    @ApiModelProperty(name = "creditId", required = false, example = "", notes = "ID")
    private Integer creditId;
    @ApiModelProperty(name = "campaignId", required = false, example = "", notes = "活动ID")
    private Integer campaignId;
    @ApiModelProperty(name = "studentCode", required = false, example = "", notes = "学号")
    private String studentCode;
    @ApiModelProperty(name = "studentName", required = false, example = "", notes = "学生姓名")
    private String studentName;
    @ApiModelProperty(name = "studentGender", required = false, example = "", notes = "学生性别")
    private String studentGender;
    @ApiModelProperty(name = "organizationId", required = false, example = "", notes = "学生系别")
    private Integer organizationId;
    @ApiModelProperty(name = "grade", required = false, example = "", notes = "学生班级")
    private String grade;
    @ApiModelProperty(name = "creditFrom", required = false, example = "", notes = "学分区间起")
    private BigDecimal creditFrom;
    @ApiModelProperty(name = "creditTo", required = false, example = "", notes = "学分区间止")
    private BigDecimal creditTo;
    @ApiModelProperty(name = "creditTimeFrom", required = false, example = "", notes = "学分获取开始时间")
    private Date creditTimeFrom;
    @ApiModelProperty(name = "creditTimeTo", required = false, example = "", notes = "学分获取结束时间")
    private Date creditTimeTo;
    @ApiModelProperty(name = "instructor", required = false, example = "", notes = "指导老师")
    private String instructor;
    @ApiModelProperty(name = "approvalType", required = false, example = "", notes = "审批类型")
    private String approvalType;
    @ApiModelProperty(name = "flag", required = true, example = "Y", notes = "标志")
    private String flag;
    @ApiModelProperty(name = "status", required = true, example = "1", notes = "状态。0 无效（取消）， 1 有效")
    private Integer status;

    public void init() {
        creditId = DataUtil.nvl(creditId, null);
        campaignId = DataUtil.nvl(campaignId, null);
        studentGender = DataUtil.nvl(studentGender, null);
        organizationId = DataUtil.nvl(organizationId, null);
        creditFrom = DataUtil.nvl(creditFrom, null);
        creditTo = DataUtil.nvl(creditTo, null);
        creditTimeFrom = DataUtil.nvl(creditTimeFrom, null);
        creditTimeTo = DataUtil.nvl(creditTimeTo, null);
        approvalType = DataUtil.nvl(approvalType, null);
        studentCode = DataUtil.likeFormat(studentCode);
        studentName = DataUtil.likeFormat(studentName);
        instructor = DataUtil.likeFormat(instructor);
        grade = DataUtil.likeFormat(grade);
        flag = DataUtil.nvl(flag, null);
        status = DataUtil.nvl(status, null);
    }
}
