package com.aps.quality.model.user;

import com.aps.quality.model.PageableSearch;
import com.aps.quality.util.DataUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SearchUserRequest extends PageableSearch {
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
    @ApiModelProperty(name = "userType", required = false, example = "", notes = "用户类型")
    private String userType;
    @ApiModelProperty(name = "organizationId", required = false, example = "", notes = "组织ID")
    private Integer organizationId;
    @ApiModelProperty(name = "flag", required = true, example = "Y", notes = "标志")
    private String flag;
    @ApiModelProperty(name = "status", required = true, example = "1", notes = "状态。0 无效（取消）， 1 有效")
    private Integer status;

    public void init() {
        userId = DataUtil.nvl(userId, null);
        userCode = DataUtil.nvl(userCode, null);
        userGender = DataUtil.nvl(userGender, null);
        userType = DataUtil.nvl(userType, null);
        flag = DataUtil.nvl(flag, null);
        status = DataUtil.nvl(status, null);
        organizationId = DataUtil.nvl(organizationId, DataUtil.getAuthorityOrganizationId());
        userName = DataUtil.likeFormat(userName);
        userPhone = DataUtil.likeFormat(userPhone);
        userEmail = DataUtil.likeFormat(userEmail);
    }
}
