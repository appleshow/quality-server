package com.aps.quality.mapper;

import com.aps.quality.entity.UserInfo;
import com.aps.quality.model.dto.UserInfoDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class UserInfoMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(UserInfo.class, UserInfoDto.class)
                .byDefault()
                .register();
    }
}