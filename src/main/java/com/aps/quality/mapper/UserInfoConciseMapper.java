package com.aps.quality.mapper;

import com.aps.quality.entity.UserConcise;
import com.aps.quality.model.dto.UserConciseDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class UserInfoConciseMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(UserConcise.class, UserConciseDto.class)
                .byDefault()
                .register();
    }
}