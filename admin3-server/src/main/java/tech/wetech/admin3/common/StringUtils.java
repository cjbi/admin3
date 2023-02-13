package tech.wetech.admin3.common;

import java.util.Map;

/**
 * @author cjbi
 */
public class StringUtils {

  /**
   * 实现简单版本 mustache 风格的模板渲染，支持<b>{{key}}<b/>格式的模板内容
   *
   * @param template   模板
   * @param attributes 属性
   * @return 渲染结果
   */
  public static String simpleRenderTemplate(String template, Map<?, ?> attributes) {
    int length = template.length();
    for (int i = 0; i < length; i++) {
      if (template.charAt(i) == '{') {
        if (length > i + 1) {
          int j = i;
          char c = template.charAt(++j);
          if (c == '{') {
            template = simpleRenderTemplate(template, length, ++j, attributes);
            length = template.length();
          }
        }
      }
    }
    return template;
  }

  private static String simpleRenderTemplate(String template, int length, int i, Map<?, ?> attributes) {
    StringBuilder valueBuilder = new StringBuilder();
    int endIndex = i - 2;
    label:
    for (; i < length; i++) {
      char c1 = template.charAt(i);
      switch (c1) {
        case ' ':
          continue;
        case '}':
          if (length > i + 1) {
            char c2 = template.charAt(++i);
            if (c2 == '}') break label;
          }
        default:
          valueBuilder.append(c1);
      }
    }
    String[] keys = valueBuilder.toString().split("\\.");
    Object value = attributes;
    for (String key : keys) {
      if (value instanceof Map) {
        value = ((Map<?, ?>) value).get(key);
      } else {
        value = null;
      }
    }
    return template.substring(0, endIndex) + value + template.substring(++i);
  }

}
