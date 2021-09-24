package com.aps.quality.service.oauth;

import com.aps.quality.entity.UserInfo;
import com.aps.quality.repository.UserInfoRepository;
import com.aps.quality.util.Const;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class UserDetailPortal implements UserDetail {
    @Resource
    private UserInfoRepository userInfoRepository;

    @Override
    public int index() {
        return 0;
    }

    @Override
    public boolean isMatch(String username) {
        return Const.UserPrefix.PORTAL.isMatch(username);
    }

    @Override
    public User getUser(String username) {
        final String code = Const.UserPrefix.PORTAL.removePrefix(username);
        final UserInfo userInfo = userInfoRepository.findByUserCode(code).orElse(null);

        if (userInfo != null) {
            final Set<GrantedAuthority> set = new HashSet<>();
            Optional.ofNullable(Const.UserPrefix.PORTAL.getDefaultRoles())
                    .ifPresent(rs -> Arrays.stream(rs).forEach(r -> set.add(new SimpleGrantedAuthority(r))));

            set.add(new SimpleGrantedAuthority(String.format("%s%d", Const.USER_ID_PREFIX, userInfo.getUserId())));

            return new User(code, userInfo.getUserPassword(), set);
        }
        throw new UsernameNotFoundException("User [" + code + "] not exists");
    }
}
