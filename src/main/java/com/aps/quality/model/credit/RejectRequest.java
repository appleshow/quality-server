package com.aps.quality.model.credit;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RejectRequest implements Serializable {
    @ApiModelProperty(name = "reason", required = false, example = "", notes = "原因")
    private String reason;
    @ApiModelProperty(name = "creditId", required = false, example = "", notes = "学分ID")
    private List<SubmitRequest> requests;
}