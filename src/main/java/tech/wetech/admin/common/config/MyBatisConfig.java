package tech.wetech.admin.common.config;

import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

@Configuration
@MapperScan(basePackages = "tech.wetech.admin.mapper")
public class MyBatisConfig {

}
