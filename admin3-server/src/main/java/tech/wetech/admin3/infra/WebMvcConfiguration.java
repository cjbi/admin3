package tech.wetech.admin3.infra;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.wetech.admin3.common.EventStore;
import tech.wetech.admin3.sys.service.SessionService;

/**
 * @author cjbi
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final SessionService sessionService;
    private final EventStore eventStore;

    public WebMvcConfiguration(SessionService sessionService, EventStore eventStore) {
        this.sessionService = sessionService;
        this.eventStore = eventStore;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration loginInterceptor = registry.addInterceptor(new AuthInterceptor(sessionService));
        loginInterceptor.addPathPatterns("/**");
        loginInterceptor.excludePathPatterns(
                "/login",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs/**",
                "favicon.ico"
        );
        InterceptorRegistration eventSubscribesInterceptor = registry.addInterceptor(new EventSubscribesInterceptor(eventStore, sessionService));
        eventSubscribesInterceptor.addPathPatterns("/**");
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.serializers(EntityBaseSerializer.instance);
    }
}
