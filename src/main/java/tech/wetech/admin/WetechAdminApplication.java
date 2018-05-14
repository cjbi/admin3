package tech.wetech.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {QuartzAutoConfiguration.class})
public class WetechAdminApplication implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(WetechAdminApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(WetechAdminApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("服务启动完成!");
    }
}
