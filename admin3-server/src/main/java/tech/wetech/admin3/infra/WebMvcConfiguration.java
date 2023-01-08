package tech.wetech.admin3.infra;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.wetech.admin3.service.SessionService;

/**
 * @author cjbi
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final SessionService sessionService;

    public WebMvcConfiguration(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration loginInterceptor = registry.addInterceptor(new LoginInterceptor(sessionService));
        loginInterceptor.addPathPatterns("/**");
        loginInterceptor.excludePathPatterns(
                "/login",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "favicon.ico"
        );
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.serializers(EntityBaseSerializer.instance);
    }
}
