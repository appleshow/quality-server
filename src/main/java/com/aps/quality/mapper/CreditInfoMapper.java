package com.aps.quality.mapper;

import com.aps.quality.entity.CreditInfo;
import com.aps.quality.model.dto.CreditInfoDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class CreditInfoMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(CreditInfo.class, CreditInfoDto.class)
                .byDefault()
                .register();
    }
}