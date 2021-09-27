package com.aps.quality.model.organization;

import com.aps.quality.util.ErrorMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreateOrganizationRequest implements Serializable {
    @ApiModelProperty(name = "organizationName", required = false, example = "", notes = "组织名称")
    private String organizationName;
    @ApiModelProperty(name = "organizationType", required = false, example = "", notes = "组织类型")
    private String organizationType;
    @ApiModelProperty(name = "organizationLevel", required = false, example = "", notes = "组织屋级")
    private Integer organizationLevel;
    @ApiModelProperty(name = "fatherOrganizationId", required = false, example = "", notes = "父组织ID")
    private Integer fatherOrganizationId;
    @ApiModelProperty(name = "flag", required = true, example = "Y", notes = "标志")
    private String flag;
    @ApiModelProperty(name = "status", required = true, example = "1", notes = "状态")
    private Integer status;
    @ApiModelProperty(name = "remark", required = false, example = "", notes = "备注")
    private String remark;

    public ErrorMessage check() {
        if (!StringUtils.hasLength(organizationName)) {
            return ErrorMessage.NAME_INVALID;
        }
        if (null == fatherOrganizationId) {
            return ErrorMessage.FATHER_ORGANIZATION_ID_INVALID;
        }

        return ErrorMessage.NULL;
    }
}
