package com.aps.quality.mapper;

import com.aps.quality.entity.CampaignInfo;
import com.aps.quality.model.dto.CampaignInfoDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class CampaignInfoMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(CampaignInfo.class, CampaignInfoDto.class)
                .byDefault()
                .register();
    }
}