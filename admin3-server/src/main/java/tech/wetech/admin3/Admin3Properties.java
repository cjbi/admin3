package tech.wetech.admin3;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cjbi
 */

@ConfigurationProperties("admin3")
public class Admin3Properties {

  private Map<String,String> logTemplates = new HashMap<>();

  public void setLogTemplates(Map<String, String> logTemplates) {
    this.logTemplates = logTemplates;
  }

  public Map<String, String> getLogTemplates() {
    return logTemplates;
  }
}
