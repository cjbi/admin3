package tech.wetech.admin3.common;

import java.util.Map;
import java.util.Random;

/**
 * @author cjbi
 */
public class StringUtils {

  public static String getRandomString(Integer num) {
    String base = "abcdefghijklmnopqrstuvwxyz0123456789";
    Random random = new Random();
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < num; i++) {
      int number = random.nextInt(base.length());
      sb.append(base.charAt(number));
    }
    return sb.toString();
  }

  public static boolean isEmpty(Object str) {
    return str == null || "".equals(str);
  }

  public static boolean isBlank(String str) {
    int strLen;
    if (str != null && (strLen = str.length()) != 0) {
      for (int i = 0; i < strLen; ++i) {
        // 判断字符是否为空格、制表符、tab
        if (!Character.isWhitespace(str.charAt(i))) {
          return false;
        }
      }
      return true;
    } else {
      return true;
    }
  }

  /**
   * 实现简单版本 mustache 风格的模板渲染，支持<b>{{key}}<b/>格式的模板内容
   *
   * @param template   模板
   * @param attributes 属性
   * @return 渲染结果
   */
  public static String simpleRenderTemplate(String template, Map<?, ?> attributes) {
    if (isBlank(template)) {
      return template;
    }
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
