package com.aps.quality.model.oauth;

import com.aps.quality.model.dto.UserInfoDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoginResponse implements Serializable {
    @ApiModelProperty(name = "token", required = true, example = "", notes = "认证信息")
    private SecurityToken securityToken;
    @ApiModelProperty(name = "userInfo", required = true, example = "", notes = "用户信息，仅用户登入时")
    private UserInfoDto userInfo;
    @ApiModelProperty(name = "changePassword", required = true, example = "", notes = "是否强制更新密码")
    private boolean changePassword;
}
