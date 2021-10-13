package com.aps.quality.service.oauth;

import com.aps.quality.model.ResponseData;
import com.aps.quality.model.oauth.LoginRequest;
import com.aps.quality.model.oauth.LoginResponse;

public interface OauthService {
    ResponseData<LoginResponse> login(LoginRequest request);

    ResponseData<Boolean> logout(String token);
}
