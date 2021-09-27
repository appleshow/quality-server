package com.aps.quality.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditApprovalConciseDto implements Serializable {
    @ApiModelProperty(name = "creditApprovalId", required = false, example = "", notes = "ID")
    private Integer creditApprovalId;
    @ApiModelProperty(name = "creditId", required = false, example = "", notes = "学分ID")
    private Integer creditId;
    @ApiModelProperty(name = "userId", required = false, example = "", notes = "用户ID")
    private Integer userId;
    @ApiModelProperty(name = "userInfo", required = false, example = "", notes = "用户信息")
    private UserConciseDto userInfo;
}
