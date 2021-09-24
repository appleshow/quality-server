/*
 *Copyright Robert Bosch GmbH. All rights reserved, also regarding any disposal, exploration, reproduction, editing,
 *distribution, as well as in the event of applications for industrial property rights.
 */
package com.aps.quality.configuration;

import com.aps.quality.util.Const;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    @Value("${resource.id}")
    private String resourceId;

    @Value("${portal.access_token.validity.period}")
    private int portalAccessTokenValiditySeconds;
    @Value("${portal.refresh_token.validity.period}")
    private int portalRefreshTokenValiditySeconds;

    @Value("${portal.security.oauth2.client.clientSecret}")
    private String portalClientSecret;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtAccessTokenConverter accessTokenConverter;

    @Resource
    private TokenStore tokenStore;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private UserDetailsService userDetailsService;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(this.authenticationManager);
        endpoints.accessTokenConverter(accessTokenConverter);
        endpoints.tokenStore(tokenStore);
        endpoints.userDetailsService(userDetailsService);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // PORTAL
                .withClient(Const.UserPrefix.PORTAL.getClientId())
                .authorizedGrantTypes("refresh_token", "password")
                .resourceIds(resourceId)
                .authorities("portal")
                .scopes(Const.UserPrefix.PORTAL.getScope())
                .accessTokenValiditySeconds(portalAccessTokenValiditySeconds)
                .refreshTokenValiditySeconds(portalRefreshTokenValiditySeconds)
                .secret(portalClientSecret);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.allowFormAuthenticationForClients();
        oauthServer.passwordEncoder(passwordEncoder);
    }
}
