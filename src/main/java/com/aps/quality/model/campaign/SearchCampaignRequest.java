package com.aps.quality.model.campaign;

import com.aps.quality.model.PageableSearch;
import com.aps.quality.util.DataUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SearchCampaignRequest extends PageableSearch {
    @ApiModelProperty(name = "campaignId", required = false, example = "", notes = "活动ID")
    private Integer campaignId;
    @ApiModelProperty(name = "campaignName", required = false, example = "", notes = "活动名称")
    private String campaignName;
    @ApiModelProperty(name = "campaignType", required = false, example = "", notes = "活动类型")
    private String campaignType;
    @ApiModelProperty(name = "campaignStart", required = false, example = "", notes = "活动开始时间")
    private Date campaignStart;
    @ApiModelProperty(name = "campaignEnd", required = false, example = "", notes = "活动结束时间")
    private Date campaignEnd;
    @ApiModelProperty(name = "sponsor", required = false, example = "", notes = "主办单位")
    private String sponsor;
    @ApiModelProperty(name = "flag", required = true, example = "Y", notes = "标志")
    private String flag;
    @ApiModelProperty(name = "status", required = true, example = "1", notes = "状态。0 无效（取消）， 1 有效")
    private Integer status;

    public void init() {
        campaignId = DataUtil.nvl(campaignId, null);
        campaignType = DataUtil.nvl(campaignType, null);
        campaignStart = DataUtil.nvl(campaignStart, null);
        campaignEnd = DataUtil.nvl(campaignEnd, null);
        campaignName = DataUtil.likeFormat(campaignName);
        sponsor = DataUtil.likeFormat(sponsor);
        flag = DataUtil.nvl(flag, null);
        status = DataUtil.nvl(status, null);
    }
}
