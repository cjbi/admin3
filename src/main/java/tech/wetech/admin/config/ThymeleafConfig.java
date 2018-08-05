package tech.wetech.admin.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThymeleafConfig {

    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

}
