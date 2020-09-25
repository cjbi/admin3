package tech.wetech.admin.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spi.service.contexts.SecurityContextBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("tech.wetech.admin.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Wetech-Admin接口文档")
                .description("Wetech-Admin | 权限管理系统")
                .termsOfServiceUrl("https://github.com/cjbi")
                .version("1.0")
                .build();
    }

    private List<SecurityScheme> securitySchemes() {
        //参数1：字段的名字，参数2：字段的键，参数3：参数位置
        return Arrays.asList(new ApiKey("Access-Token", "Access-Token", "header"));
    }

    //认证的上下文，这里面需要指定哪些接口需要认证
    private List<SecurityContext> securityContexts() {
        SecurityContextBuilder builder = SecurityContext.builder().securityReferences(securityReferences());
        //指定需要认证的path，大写的注意，这里就用到了配置文件里面的URL，需要自己实现path选择的逻辑
//        builder.forPaths(null);
        return Arrays.asList(builder.build());
    }

    //这个方法是验证的作用域，不能漏了
    private List<SecurityReference> securityReferences() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        return Arrays.asList(
                new SecurityReference("Access-Token", new AuthorizationScope[]{authorizationScope}));
    }

}
