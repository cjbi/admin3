package tech.wetech.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * @author cjbi
 */
@SpringBootApplication
@EnableOpenApi
@MapperScan(basePackages = "tech.wetech.admin.mapper")
public class WetechAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(WetechAdminApplication.class, args);
    }
}
