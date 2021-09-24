/*
 *Copyright Robert Bosch GmbH. All rights reserved, also regarding any disposal, exploration, reproduction, editing,
 *distribution, as well as in the event of applications for industrial property rights.
 */
package com.aps.quality.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class OAuthSecurityCommonConfiguration {
    @Value("${jwt.access.token.signing.key:623846b2db2011e79296cec278b6b50a}")
    private String tokenConverterSignKey;

    @Resource
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Bean
    public RequestMatcher oAuthRequestedMatcher() {
        return request -> {
            final String auth = request.getHeader("Authorization");
            // Determine if the client request contained an OAuth Authorization
            final boolean haveOauth2Token = (auth != null) && auth.startsWith("Bearer");
            final boolean haveAccessToken = request.getParameter("access_token") != null;

            return haveOauth2Token || haveAccessToken;
        };
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        final JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter() {
            /**
             * enhancement, may need to add custom fields
             */
            @Override
            public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
                /*
                String username = authentication.getUserAuthentication().getName();
                User user = (User) authentication.getUserAuthentication().getPrincipal();
                final Map<String, Object> additionalInformation = new HashMap<>();
                additionalInformation.put("username", username);
                additionalInformation.put("roles", user.getAuthorities().stream().filter(g -> g.getAuthority().startsWith("ROLE_")).map(g -> g.getAuthority().replace("ROLE_", "")).toArray());
                additionalInformation.put("shop", user.getAuthorities().stream().filter(g -> g.getAuthority().startsWith("SHOP_")).findFirst().map(g -> g.getAuthority().replace("SHOP_", "")).orElse("0"));
                ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
                 */
                return super.enhance(accessToken, authentication);
            }
        };
        accessTokenConverter.setSigningKey(tokenConverterSignKey);

        return accessTokenConverter;
    }

    @Bean
    public ResourceServerTokenServices defaultTokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenEnhancer(accessTokenConverter());
        defaultTokenServices.setTokenStore(new JdbcTokenStore(dataSource));

        return defaultTokenServices;
    }
}
