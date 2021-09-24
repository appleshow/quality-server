package com.aps.quality.model.authority;

import com.aps.quality.model.PageableSearch;
import com.aps.quality.util.DataUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SearchGroupRequest extends PageableSearch implements Serializable {
    @ApiModelProperty(name = "groupId", required = false, example = "", notes = "权限组ID")
    private Integer groupId;
    @ApiModelProperty(name = "groupName", required = false, example = "", notes = "权限组名称")
    private String groupName;
    @ApiModelProperty(name = "status", required = true, example = "1", notes = "状态。0 无效（取消）， 1 有效")
    private Integer status;

    public void init() {
        groupName = DataUtil.likeFormat(groupName);
    }
}
