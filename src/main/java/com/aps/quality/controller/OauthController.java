package com.aps.quality.controller;

import com.aps.quality.model.ResponseData;
import com.aps.quality.model.oauth.LoginRequest;
import com.aps.quality.model.oauth.LoginResponse;
import com.aps.quality.service.oauth.OauthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Api(tags = "认证接口")
@RestController
@RequestMapping("/oauth")
public class OauthController extends ExceptionController {
    @Resource
    private OauthService oauthService;

    @ApiOperation("用户/设备登入")
    @PostMapping("login")
    public ResponseData<LoginResponse> login(@RequestBody final LoginRequest request) {
        return oauthService.login(request);
    }

    @ApiOperation("用户/设备登出")
    @PostMapping("logout")
    public ResponseData<Boolean> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) final String token) {
        return oauthService.logout(token);
    }
}
