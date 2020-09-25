package tech.wetech.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/wetech-admin-ui/");
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = jackson2HttpMessageConverter.getObjectMapper();

        //不显示为null的字段
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //序列化枚举是以ordinal()来输出
//        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
        SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);

        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        //放到第一个
        converters.add(0, jackson2HttpMessageConverter);
    }

    @Bean
    public ErrorPageRegistrar myErrorPageRegistrar() {
        return registry -> registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/index.html"));
    }
}

