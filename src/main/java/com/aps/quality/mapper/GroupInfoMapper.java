package com.aps.quality.mapper;

import com.aps.quality.entity.GroupInfo;
import com.aps.quality.model.dto.GroupInfoDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class GroupInfoMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(GroupInfo.class, GroupInfoDto.class)
                .byDefault()
                .register();
    }
}