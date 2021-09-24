package com.aps.quality.mapper;

import com.aps.quality.entity.GroupRoleInfo;
import com.aps.quality.model.dto.GroupRoleInfoDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class GroupRoleInfoMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(GroupRoleInfo.class, GroupRoleInfoDto.class)
                .byDefault()
                .register();
    }
}