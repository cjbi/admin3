package tech.wetech.admin3.infra;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import jakarta.persistence.*;
import tech.wetech.admin3.sys.model.BaseEntity;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解决jpa关联实体序列化的问题
 *
 * @author cjbi
 * @date 2022/9/16
 */
public class BaseEntitySerializer extends StdSerializer<BaseEntity> {

  public final static BaseEntitySerializer instance = new BaseEntitySerializer(BaseEntity.class);

  private final List<Class<? extends Annotation>> IGNORE_ANNOTATIONS = Arrays.asList(ElementCollection.class, OneToMany.class, OneToOne.class, ManyToOne.class, ManyToMany.class, Embedded.class);

  protected BaseEntitySerializer(Class<BaseEntity> t) {
    super(t);
  }

  @Override
  public void serialize(BaseEntity value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    Map<String, Object> data = new HashMap<>();
    data.putAll(invokeGetter(value, value.getClass(), value.getClass().getDeclaredFields()));
    data.putAll(invokeGetter(value, BaseEntity.class, BaseEntity.class.getDeclaredFields()));
    provider.defaultSerializeValue(data, gen);
  }

  private Map<String, Object> invokeGetter(BaseEntity value, Class<? extends BaseEntity> aClass, Field[] declaredFields) {
    Map<String, Object> data = new HashMap<>();

    for (Field declaredField : declaredFields) {
      boolean flag = false;
      for (Annotation annotation : declaredField.getAnnotations()) {
        if (IGNORE_ANNOTATIONS.contains(annotation.annotationType())) {
          flag = true;
          break;
        }
      }
      if (!flag) {
        String fieldName = declaredField.getName();
        try {
          String upperCamelCase = toUpperCamelCase(fieldName);
          String getter = "get" + upperCamelCase;
          String is = "is" + upperCamelCase;
          for (Method declaredMethod : aClass.getDeclaredMethods()) {
            if (getter.equals(declaredMethod.getName()) || is.equals(declaredMethod.getName())) {
              data.put(fieldName, declaredMethod.invoke(value));
            }
          }
        } catch (InvocationTargetException | IllegalAccessException ignored) {

        }
      }

    }
    return data;
  }

  private String toUpperCamelCase(String str) {
    char[] cs = str.toCharArray();
    cs[0] -= 32;
    return String.valueOf(cs);
  }


}
