package tech.wetech.admin.config;

import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@MapperScan(basePackages = "tech.wetech.admin.modules.*.mapper")
public class MyBatisConfig {

}
