package com.aps.quality.mapper;

import com.aps.quality.entity.OrganizationInfo;
import com.aps.quality.model.dto.OrganizationInfoDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class OrganizationInfoMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(OrganizationInfo.class, OrganizationInfoDto.class)
                .byDefault()
                .register();
    }
}