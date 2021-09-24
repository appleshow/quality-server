package com.aps.quality.model.oauth;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LoginRequest implements Serializable {
    @ApiModelProperty(name = "userCode", required = true, example = "", notes = "用户代码")
    private String userCode;
    @ApiModelProperty(name = "userPassword", required = true, example = "", notes = "用户密码")
    private String userPassword;

    public LoginRequest desensitization() {
        final LoginRequest loginRequest = new LoginRequest();

        BeanUtils.copyProperties(this, loginRequest);
        loginRequest.setUserPassword("***");

        return loginRequest;
    }
}
