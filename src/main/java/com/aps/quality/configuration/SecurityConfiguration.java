/*
 *Copyright Robert Bosch GmbH. All rights reserved, also regarding any disposal, exploration, reproduction, editing,
 *distribution, as well as in the event of applications for industrial property rights.
 */
package com.aps.quality.configuration;

import com.aps.quality.service.oauth.UserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

/**
 * Security Configuration
 * To config the customization security strategy
 */
@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Resource
    private List<UserDetail> userDetails;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/v2/api-docs", "/webjars/springfox-swagger-ui/**", "/swagger-ui.html", "/swagger-resources", "/swagger-resources/**").permitAll()
                .antMatchers("/oauth/token").permitAll()
                .antMatchers("/oauth/login").permitAll()
                .antMatchers("/image/**").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
                .and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new DefaultAuthenticationEntryPoint());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return username -> {
            log.info("Username is : {}", username);
            if (!StringUtils.hasLength(username)) {
                throw new UsernameNotFoundException("Username could not be null");
            }
            final UserDetail userDetail = userDetails.stream().sorted(Comparator.comparingInt(UserDetail::index)).filter(u -> u.isMatch(username)).findAny().orElse(null);
            if (null == userDetail) {
                throw new UsernameNotFoundException("User [" + username + "] not exists");
            } else {
                return userDetail.getUser(username);
            }
        };

    }

}
