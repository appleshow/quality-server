package com.aps.quality.mapper;

import com.aps.quality.entity.CertificateInfo;
import com.aps.quality.model.dto.CertificateInfoDto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class CertificateInfoMapper extends ConfigurableMapper {

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(CertificateInfo.class, CertificateInfoDto.class)
                .byDefault()
                .register();
    }
}