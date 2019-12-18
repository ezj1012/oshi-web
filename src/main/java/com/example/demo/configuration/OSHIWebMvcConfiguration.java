package com.example.demo.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.example.demo.utils.ConstantUtil;

/**
 * 覆盖spring mvc 的一些默认的配置.
 * 
 * @author XiongJian
 *
 */
@Configuration
public class OSHIWebMvcConfiguration extends WebMvcConfigurationSupport {

    /**
     * url中 xxx/ 默认跳转到 xxx/index.*
     */
    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "index.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/")
                // .addResourceLocations("classpath:/templates/")
                .addResourceLocations("classpath:/templates/");
        super.addResourceHandlers(registry);
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setCharset(ConstantUtil.DEF_CHARSET);
        fastJsonConfig.setSerializerFeatures(SerializerFeature.SkipTransientField);
        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.APPLICATION_JSON_UTF8);
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        fastConverter.setSupportedMediaTypes(mediaTypeList);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        converters.add(fastConverter);
    }
}
