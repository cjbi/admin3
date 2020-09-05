package tech.wetech.admin.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;


/**
 * @author cjbi
 */
public class JSONUtil {

	private static final ObjectMapper JSON = new ObjectMapper();

	static {
		JSON.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		JSON.configure(SerializationFeature.INDENT_OUTPUT, false);
		//不显示为null的字段
		JSON.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		//序列化枚举是以ordinal()来输出
		JSON.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
		SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
		JSON.registerModule(simpleModule);
		JSON.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static String toJSONString(Object obj) {
		try {
			return JSON.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T toObject(String json, Class<T> clz) {
		try {
			return JSON.readValue(json, clz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
