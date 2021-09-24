/*
 *Copyright Robert Bosch GmbH. All rights reserved, also regarding any disposal, exploration, reproduction, editing,
 *distribution, as well as in the event of applications for industrial property rights.
 */
/**
 * Created by chakri.vadde on 1/19/2016.
 */

package com.aps.quality.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Configuration
@EnableSwagger2WebMvc
@Profile("!prd")
public class SwaggerConfig {
    @Value("${swagger.v2.host:}")
    private String host;

    @Bean
    public Docket api2() {
        final ParameterBuilder tokenPar = new ParameterBuilder();
        final List<Parameter> pars = new ArrayList<>();

        tokenPar.name("Authorization").description("Authorization Token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(paths())
                .build()
                .host(host)
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "信发 Service",
                "",
                "1.0",
                "Some " + "License", new Contact("APS Power Tools", "http://www.baidu.com", "appleshow@hotmail.com"),
                "APS License type",
                "http://www.baidu.com",
                new ArrayList<>());
    }

    private Predicate<String> paths() {
        return PathSelectors.regex("/oauth/login")
                .or(PathSelectors.regex("/user.*"))
                .or(PathSelectors.regex("/authority.*"))
                .or(PathSelectors.regex("/organization.*"))
                .or(PathSelectors.regex("/material.*"))
                .or(PathSelectors.regex("/basic.*"));
    }
}