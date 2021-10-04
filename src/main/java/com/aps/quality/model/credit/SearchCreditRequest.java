package com.aps.quality.model.credit;

import com.aps.quality.model.PageableSearch;
import com.aps.quality.util.DataUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SearchCreditRequest extends PageableSearch {
    @ApiModelProperty(name = "creditIds", required = false, example = "", notes = "学分ID列表")
    private Integer[] creditIds;
    @ApiModelProperty(name = "userId", required = false, example = "", notes = "用户ID")
    private Integer userId;
    @ApiModelProperty(name = "campaignType", required = false, example = "", notes = "项目类型")
    private String campaignType;
    @ApiModelProperty(name = "campaignName", required = false, example = "", notes = "项目名称")
    private String campaignName;
    @ApiModelProperty(name = "groupByCampaign", required = false, example = "", notes = "是否按项目分组")
    private boolean groupByCampaign;
    @ApiModelProperty(name = "groupByUser", required = false, example = "", notes = "是否按用户分组")
    private boolean groupByUser;
    @ApiModelProperty(name = "userCode", required = false, example = "", notes = "学号")
    private String userCode;
    @ApiModelProperty(name = "userName", required = false, example = "", notes = "学生姓名")
    private String userName;
    @ApiModelProperty(name = "userGender", required = false, example = "", notes = "学生性别")
    private String userGender;
    @ApiModelProperty(name = "organizationId", required = false, example = "", notes = "学生系别")
    private Integer organizationId;
    @ApiModelProperty(name = "creditTimeFrom", required = false, example = "", notes = "学分获取开始时间")
    private Date creditTimeFrom;
    @ApiModelProperty(name = "creditTimeTo", required = false, example = "", notes = "学分获取结束时间")
    private Date creditTimeTo;
    @ApiModelProperty(name = "instructor", required = false, example = "", notes = "指导老师")
    private String instructor;
    @ApiModelProperty(name = "status", required = true, example = "1", notes = "状态")
    private Integer status;
    @ApiModelProperty(name = "statusFrom", required = true, example = "1", notes = "状态起")
    private Integer statusFrom;
    @ApiModelProperty(name = "statusTo", required = true, example = "1", notes = "状态止")
    private Integer statusTo;
    @ApiModelProperty(name = "createBy", required = false, example = "", notes = "创建人")
    private String createBy;
    @ApiModelProperty(name = "matchUser", required = false, example = "", notes = "是否按用户匹配")
    private boolean matchUser;

    public void init() {
        creditIds = DataUtil.nvl(creditIds, null);
        userId = DataUtil.nvl(userId, null);
        campaignType = DataUtil.nvl(campaignType, null);
        campaignName = DataUtil.likeFormat(campaignName);
        userCode = DataUtil.nvl(userCode, null);
        userName = DataUtil.likeFormat(userName);
        userGender = DataUtil.nvl(userGender, null);
        organizationId = DataUtil.nvl(organizationId, null);
        creditTimeFrom = DataUtil.nvl(creditTimeFrom, null);
        creditTimeTo = DataUtil.nvl(creditTimeTo, null);
        instructor = DataUtil.nvl(instructor, null);
        status = DataUtil.nvl(status, null);
        createBy = DataUtil.nvl(createBy, null);
    }
}
