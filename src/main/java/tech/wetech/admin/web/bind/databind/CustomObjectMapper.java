package tech.wetech.admin.web.bind.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;

/**
 * 自定义JSON转换器
 *
 * @author cjbi
 */
public class CustomObjectMapper extends ObjectMapper {

    public CustomObjectMapper() {
        super();
        SimpleModule simpleModule = new SimpleModule();
        // 所有Long类型转换String类型
//        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        // 禁用空对象转换json校验
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.registerModule(simpleModule);
        // 设置null转换""
//        this.getSerializerProvider().setNullValueSerializer(new NullSerializer());
        // 设置日期转换yyyy-MM-dd HH:mm:ss
        // setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 不显示为null的字段
        // this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    // null的JSON序列
    private class NullSerializer extends JsonSerializer<Object> {
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider)
                throws IOException, JsonProcessingException {
            jgen.writeString("");
        }
    }

}
