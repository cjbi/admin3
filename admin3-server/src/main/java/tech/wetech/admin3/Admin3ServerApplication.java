package tech.wetech.admin3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(Admin3Properties.class)
@SpringBootApplication
public class Admin3ServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(Admin3ServerApplication.class, args);
  }

}
