package com.aps.quality.model.organization;

import com.aps.quality.util.ErrorMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UpdateOrganizationRequest implements Serializable {
    @ApiModelProperty(name = "organizationId", required = false, example = "", notes = "组织ID")
    private Integer organizationId;
    @ApiModelProperty(name = "organizationName", required = false, example = "", notes = "组织名称")
    private String organizationName;
    @ApiModelProperty(name = "organizationType", required = false, example = "", notes = "组织类型")
    private String organizationType;
    @ApiModelProperty(name = "flag", required = true, example = "Y", notes = "标志")
    private String flag;
    @ApiModelProperty(name = "status", required = true, example = "1", notes = "状态")
    private Integer status;
    @ApiModelProperty(name = "remark", required = false, example = "", notes = "备注")
    private String remark;

    @ApiModelProperty(name = "version", required = false, example = "1", notes = "数据版本号。创建无须此参数，更新必须携带此参数")
    private Integer version;

    public ErrorMessage check() {
        if (null == organizationId) {
            return ErrorMessage.ID_CAN_NOT_BE_NULL;
        }
        if (null == version) {
            return ErrorMessage.VERSION_IS_NULL;
        }

        return ErrorMessage.NULL;
    }
}
