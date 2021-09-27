package com.aps.quality.model.campaign;

import com.aps.quality.util.ErrorMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UpdateCampaignRequest implements Serializable {
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
    @ApiModelProperty(name = "status", required = true, example = "1", notes = "状态")
    private Integer status;
    @ApiModelProperty(name = "remark", required = false, example = "", notes = "备注")
    private String remark;
    @ApiModelProperty(name = "version", required = false, example = "1", notes = "数据版本号。创建无须此参数，更新必须携带此参数")
    private Integer version;

    public ErrorMessage check() {
        if (null == campaignId) {
            return ErrorMessage.ID_CAN_NOT_BE_NULL;
        }
        if (null == version) {
            return ErrorMessage.VERSION_IS_NULL;
        }

        return ErrorMessage.NULL;
    }
}
