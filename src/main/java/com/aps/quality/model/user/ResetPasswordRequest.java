package com.aps.quality.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResetPasswordRequest implements Serializable {
    @ApiModelProperty(name = "oldPassword", required = true, example = "**", notes = "旧密码")
    private String oldPassword;
    @ApiModelProperty(name = "newPassword", required = true, example = "**", notes = "新密码")
    private String newPassword;
}
