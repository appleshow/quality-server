package com.aps.quality.mapper;

import com.aps.quality.entity.Metadata;
import com.aps.quality.model.dto.MetadataDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class MetadataMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(Metadata.class, MetadataDto.class)
                .byDefault()
                .register();
    }
}