/**
 * Copyright (C) 2006-2019 Wisedu All rights reserved
 * Author：zhangguifeng
 * Date：2020/3/4
 * Description: kenife4j配置类
 */
package com.ruoyi.web.controller.tool;

import com.github.xiaoymin.knife4j.spring.annotations.EnableSwaggerBootstrapUi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * kenife4j配置类
 * @author zhangguifeng
 * @create 2020-03-04 16:40
 **/
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUi
public class Swagger2Config {

    @Bean
    public Docket createRestApiDoc() {
        return new Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.ruoyi.web.controller.fac"))
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("接口文档").description("服务相关接口")
            .contact(new Contact("张桂锋", null, "chriszh123@sina.com"))
            .version("1.1.1").build();
    }
}
