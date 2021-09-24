package com.aps.quality.model.authority;

import com.aps.quality.model.PageableSearch;
import com.aps.quality.util.DataUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SearchRoleRequest extends PageableSearch implements Serializable {
    @ApiModelProperty(name = "roleId", required = false, example = "", notes = "权限ID")
    private Integer roleId;
    @ApiModelProperty(name = "roleName", required = false, example = "", notes = "权限名称")
    private String roleName;
    @ApiModelProperty(name = "status", required = true, example = "1", notes = "状态。0 无效（取消）， 1 有效")
    private Integer status;

    public void init() {
        roleName = DataUtil.likeFormat(roleName);
    }
}
