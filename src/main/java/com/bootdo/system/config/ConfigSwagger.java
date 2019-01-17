package com.bootdo.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * ${DESCRIPTION}
 *
 * @author edison
 * @create 2017-01-02 23:53
 */
@Configuration
@EnableSwagger2
public class ConfigSwagger {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bootdo.app.controller"))
                .build().pathMapping("/tv/consignment-master");
                //.build().pathMapping("/");
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("后台管理系统")
                .description("api")
                .contact("lim")
                .version("1.0.0")
                .build();
    }
}