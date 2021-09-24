package com.aps.quality.mapper;

import com.aps.quality.entity.UserOrganizationInfo;
import com.aps.quality.model.dto.UserOrganizationInfoDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class UserOrganizationInfoMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(UserOrganizationInfo.class, UserOrganizationInfoDto.class)
                .byDefault()
                .register();
    }
}