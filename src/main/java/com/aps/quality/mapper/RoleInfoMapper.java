package com.aps.quality.mapper;

import com.aps.quality.entity.RoleInfo;
import com.aps.quality.model.dto.RoleInfoDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleInfoMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(RoleInfo.class, RoleInfoDto.class)
                .byDefault()
                .register();
    }
}