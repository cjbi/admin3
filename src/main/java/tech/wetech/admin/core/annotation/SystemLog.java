package tech.wetech.admin.core.annotation;

import java.lang.annotation.*;

/**
 * 日志注解 拦截controller请求
 * <p>
 * Created by cjbi on 2017/12/21.
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {

//  String module()  default "";  //例：模块名称 系统管理-用户管理－列表页面 暂不需要

    /**
     * 日志描述<br/>
     * 例如：新增用户
     */
    String value() default "";

    /**
     * 日志描述<br/>
     * 例如：新增用户
     */
    String desc()  default "";

}
