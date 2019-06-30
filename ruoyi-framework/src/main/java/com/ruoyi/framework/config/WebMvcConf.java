package com.ruoyi.framework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 映射图片访问路径
 * Created by zgf
 * Date 2019/6/1 21:34
 * Description
 */
@Configuration
public class WebMvcConf extends WebMvcConfigurerAdapter {

    @Value("${file.imageReturnPath}")
    private String imageReturnPath;
    @Value("${file.imagesPath}")
    private String imagesPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //imageReturnPath:映射前缀，imagesPath：图片映射路径
        registry.addResourceHandler(imageReturnPath).addResourceLocations(imagesPath);
    }
}
