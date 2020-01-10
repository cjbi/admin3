package tech.wetech.admin.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;


public class JsonUtil {

	private static final ObjectMapper JSON = new ObjectMapper();

	static {
		JSON.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		JSON.configure(SerializationFeature.INDENT_OUTPUT, Boolean.TRUE);
		//不显示为null的字段
		JSON.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		//序列化枚举是以ordinal()来输出
		JSON.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
		SimpleModule simpleModule = new SimpleModule();
//        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
		JSON.registerModule(simpleModule);
		//不校验未知属性com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: Unrecognized field "modelName" (class tech.wetech.admin.web.dto.system.GeneratorDto), not marked as ignorable (31 known properties: "jspTargetFolder", "controllerName", "generateKeys", "controllerPackage", "domainObjectName", "daoTargetFolder", "daoPackage", "moduleName", "controllerTargetFolder", "modelPackageTargetFolder", "modelPackage", "comment", "serviceImplName", "projectFolder", "mappingXMLTargetFolder", "ignoredColumns", "mapperName", "annotation", "columnOverrides", "jspName", "serviceImplTargetFolder", "tableName", "useTableNameAlias", "serviceImplPackage", "mappingXMLPackage", "serviceName", "offsetLimit", "serviceTargetFolder", "needToStringHashcodeEquals", "useActualColumnNames", "servicePackage"])
		JSON.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static String toJson(Object obj) {
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
