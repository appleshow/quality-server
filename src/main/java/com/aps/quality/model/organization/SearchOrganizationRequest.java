package com.aps.quality.model.organization;

import com.aps.quality.model.PageableSearch;
import com.aps.quality.util.DataUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SearchOrganizationRequest extends PageableSearch {
    @ApiModelProperty(name = "organizationId", required = false, example = "", notes = "组织ID")
    private Integer organizationId;
    @ApiModelProperty(name = "organizationName", required = false, example = "", notes = "组织名称")
    private String organizationName;
    @ApiModelProperty(name = "organizationType", required = false, example = "", notes = "组织类型")
    private String organizationType;
    @ApiModelProperty(name = "fatherOrganizationId", required = false, example = "", notes = "父组织ID")
    private Integer fatherOrganizationId;
    @ApiModelProperty(name = "flag", required = true, example = "Y", notes = "标志")
    private String flag;
    @ApiModelProperty(name = "status", required = true, example = "1", notes = "状态")
    private Integer status;

    public void init() {
        organizationId = DataUtil.nvl(organizationId, null);
        fatherOrganizationId = DataUtil.nvl(fatherOrganizationId, null);
        organizationName = DataUtil.likeFormat(organizationName);
        organizationType = DataUtil.likeFormat(organizationType);
        flag = DataUtil.nvl(flag, null);
        status = DataUtil.nvl(status, null);
    }
}
