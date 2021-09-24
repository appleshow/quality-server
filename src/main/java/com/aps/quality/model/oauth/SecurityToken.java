package com.aps.quality.model.oauth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SecurityToken implements Serializable {
    @ApiModelProperty(name = "scope", required = true, example = "", notes = "accessToken")
    private String scope;
    @ApiModelProperty(name = "tokenType", required = true, example = "", notes = "token 类型")
    private String tokenType;
    @ApiModelProperty(name = "expiresIn", required = true, example = "", notes = "过期时间")
    private Integer expiresIn;
    @ApiModelProperty(name = "refreshToken", required = true, example = "", notes = "刷新token")
    private String refreshToken;
    @ApiModelProperty(name = "username", required = true, example = "", notes = "用户代码")
    private String username;
    @ApiModelProperty(name = "accessToken", required = true, example = "", notes = "token")
    private String accessToken;
}
