package com.aps.quality.model.credit;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SubmitRequest implements Serializable {
    @ApiModelProperty(name = "creditIds", required = false, example = "", notes = "学分ID列表")
    private String creditIds;
    @ApiModelProperty(name = "creditId", required = false, example = "", notes = "学分ID")
    private Integer creditId;
}
