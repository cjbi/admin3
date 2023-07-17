package tech.wetech.admin3.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import tech.wetech.admin3.infra.BaseEntitySerializer;

import java.io.IOException;
import java.util.*;

/**
 * @author cjbi
 */
public class JsonUtils {

  private static final JsonMapper JSON;

  static {
    JsonMapper.Builder builder = new JsonMapper().rebuild();
    builder.serializationInclusion(JsonInclude.Include.NON_NULL);
//        JSON.configure(SerializationFeature.INDENT_OUTPUT, false);
    //不显示为null的字段
    builder.serializationInclusion(JsonInclude.Include.NON_NULL);
    //序列化枚举是以ordinal()来输出
    builder.addModule(new JavaTimeModule());
    builder.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    builder.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    builder.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
    SimpleModule module = new SimpleModule();
    module.addSerializer(BaseEntitySerializer.instance);
    builder.addModule(module);
    JSON = builder.build();
  }

  public static String stringify(Object obj) {
    try {
      return JSON.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static <T> T parseToObject(String json, Class<T> clz) {
    try {
      return JSON.readValue(json, clz);
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static Map<String, Object> parseToMap(String json) {
    try {
      return JSON.readValue(json, new TypeReference<>() {
      });
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static <T> List<T> parseToList(String json, Class<T> clz) {
    try {
      List<T> result = new ArrayList<>();
      JsonNode jsonNode = JSON.readTree(json);
      for (JsonNode itemNode : jsonNode) {
        result.add(JSON.readValue(itemNode.toString(), clz));
      }
      return result;
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  public static <T> Set<T> parseToSet(String json, Class<T> clz) {
    try {
      Set<T> result = new HashSet<>();
      JsonNode jsonNode = JSON.readTree(json);
      for (JsonNode itemNode : jsonNode) {
        result.add(JSON.readValue(itemNode.toString(), clz));
      }
      return result;
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

}
