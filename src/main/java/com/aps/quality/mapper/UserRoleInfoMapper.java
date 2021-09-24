package com.aps.quality.mapper;

import com.aps.quality.entity.UserRoleInfo;
import com.aps.quality.model.dto.UserRoleInfoDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class UserRoleInfoMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(UserRoleInfo.class, UserRoleInfoDto.class)
                .byDefault()
                .register();
    }
}