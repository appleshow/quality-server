/*
 *Copyright Robert Bosch GmbH. All rights reserved, also regarding any disposal, exploration, reproduction, editing,
 *distribution, as well as in the event of applications for industrial property rights.
 */
package com.aps.quality.configuration;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Date;
import java.util.Map;

public class CustomizeJwtTokenStore extends JwtTokenStore {


    /**
     * Create a JwtTokenStore with this token enhancer (should be shared with the DefaultTokenServices if used).
     *
     * @param jwtTokenEnhancer
     */
    public CustomizeJwtTokenStore(JwtAccessTokenConverter jwtTokenEnhancer) {
        super(jwtTokenEnhancer);
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        final OAuth2AccessToken accessToken = super.readAccessToken(tokenValue);
        final String username = getUsername(accessToken);

        if (null == username) {
            ((DefaultOAuth2AccessToken) accessToken).setExpiration(new Date(System.currentTimeMillis() - 1));
            return accessToken;
        }

        return accessToken;
    }


    private String getUsername(OAuth2AccessToken token) {
        Map<String, Object> map = token.getAdditionalInformation();
        if (!map.isEmpty()) {
            return (String) map.get("username");
        }
        return null;
    }
}
