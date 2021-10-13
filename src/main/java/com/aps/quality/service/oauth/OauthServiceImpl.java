package com.aps.quality.service.oauth;

import com.aps.quality.entity.UserInfo;
import com.aps.quality.mapper.UserInfoMapper;
import com.aps.quality.model.ResponseData;
import com.aps.quality.model.dto.UserInfoDto;
import com.aps.quality.model.oauth.LoginRequest;
import com.aps.quality.model.oauth.LoginResponse;
import com.aps.quality.model.oauth.SecurityToken;
import com.aps.quality.repository.UserInfoRepository;
import com.aps.quality.service.OperationLogService;
import com.aps.quality.util.Const;
import com.aps.quality.util.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;

@Slf4j
@Service
public class OauthServiceImpl extends OperationLogService implements OauthService {
    @Resource
    private RestTemplate customRestTemplate;
    @Value("${security.oauth2.client.accessTokenUri}")
    private String accessTokenUri;
    @Value("${portal.tag}")
    private String portalClientSecret;

    @Resource
    private UserInfoRepository userInfoRepository;
    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private TokenStore tokenStore;

    @Override
    public ResponseData<LoginResponse> login(final LoginRequest request) {
        log.info("call login: {}", request.desensitization());

        final LoginResponse loginResponse = new LoginResponse();
        final MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

        parameters.set("username", String.format("%s%s", Const.UserPrefix.PORTAL.getCode(), request.getUserCode()));
        parameters.set("password", request.getUserPassword());
        parameters.set("grant_type", "password");
        parameters.set("scope", Const.UserPrefix.PORTAL.getScope());
        parameters.set("client_id", Const.UserPrefix.PORTAL.getClientId());
        parameters.set("client_secret", portalClientSecret);

        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        final HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity(parameters, httpHeaders);
        final UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(accessTokenUri);
        try {
            log.info("try to get token for {}", request.getUserCode());
            loginResponse.setSecurityToken(customRestTemplate.postForObject(urlBuilder.build().toUriString(), httpEntity, SecurityToken.class));
        } catch (Exception e) {
            log.error("try to get token for {} got an error: {}", request.getUserCode(), e);
            saveLog(Const.OperationType.LOGIN, null, request, e.getMessage());
            return new ResponseData(!StringUtils.hasLength(e.getMessage()) ? ErrorMessage.FAIL_TO_GET_TOKEN.getDescription() : e.getMessage());
        }

        log.info("call userWithGroupRepository.findByCode({})", request.getUserCode());
        final UserInfo user = userInfoRepository.findByUserCode(request.getUserCode()).orElse(null);
        if (null != user) {
            if (Const.Status.INVALID.equalWithCode(user.getStatus())) {
                saveLog(Const.OperationType.LOGIN, null, ErrorMessage.USER_INVALID);
                return new ResponseData(ErrorMessage.USER_INVALID);
            }
            loginResponse.setUserInfo(userInfoMapper.map(user, UserInfoDto.class));
            if (request.getUserPassword().equals(user.getUserCode())) {
                loginResponse.setChangePassword(true);
            } else {
                loginResponse.setChangePassword(false);
            }
        }
        saveLog(Const.OperationType.LOGIN, request.getUserCode(), Const.OperationSubType.USER, request);

        return new ResponseData<>(loginResponse);
    }

    @Override
    public ResponseData<Boolean> logout(String token) {
        log.info("call logout()");
        if (StringUtils.hasLength(token)) {
            log.info("call tokenStore.removeAccessToken()");
            tokenStore.removeAccessToken(tokenStore.readAccessToken(token.replace("Bearer", "").trim()));
        }

        return new ResponseData<>(true);
    }
}