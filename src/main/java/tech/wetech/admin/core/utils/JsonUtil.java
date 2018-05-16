package tech.wetech.admin.core.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;


public class JsonUtil {
	private static JsonUtil ju;
	private static JsonFactory jf;
	private static ObjectMapper mapper;
	private JsonUtil(){}
	
	public static JsonUtil getInstance() {
		if(ju==null) {
			ju = new JsonUtil();
		}
		return ju;
	}
	
	public static ObjectMapper getMapper() {
		if(mapper==null) {
			mapper = new ObjectMapper();
			//不校验未知属性com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: Unrecognized field "modelName" (class tech.wetech.admin.web.dto.system.GeneratorDto), not marked as ignorable (31 known properties: "jspTargetFolder", "controllerName", "generateKeys", "controllerPackage", "domainObjectName", "daoTargetFolder", "daoPackage", "moduleName", "controllerTargetFolder", "modelPackageTargetFolder", "modelPackage", "comment", "serviceImplName", "projectFolder", "mappingXMLTargetFolder", "ignoredColumns", "mapperName", "annotation", "columnOverrides", "jspName", "serviceImplTargetFolder", "tableName", "useTableNameAlias", "serviceImplPackage", "mappingXMLPackage", "serviceName", "offsetLimit", "serviceTargetFolder", "needToStringHashcodeEquals", "useActualColumnNames", "servicePackage"])
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}
		return mapper;
	}
	
	public static JsonFactory getFactory() {
		if(jf==null) {
			jf = new JsonFactory();
		}
		return jf;
	}
	
	public String obj2json(Object obj) {
		JsonGenerator jg = null;
		try {
			jf = getFactory();
			mapper = getMapper();
			StringWriter out = new StringWriter();
			jg = jf.createGenerator(out);
			mapper.writeValue(jg, obj);
			return out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(jg!=null) {
					jg.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Object json2obj(String json,Class<?> clz) {
		try {
			mapper = getMapper();
			return mapper.readValue(json,clz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
