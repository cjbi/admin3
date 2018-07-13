package tech.wetech.admin.common.base;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import tech.wetech.admin.common.exception.BizException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author cjbi
 */
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success;

    private String code;

    private String msg;

    private String errorMsg;

    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static Result success() {
        return Result.success(null);
    }

    public static <T> Result success(T obj) {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(ResultCodeEnum.OK.getCode());
        result.setMsg(ResultCodeEnum.OK.getMsg());
        if (obj == null) {
            // 若返回数据为null 统一返回给前端{}
            result.setData(new HashMap<>(1));
        } else {
            result.setData(obj);
        }
        return result;
    }

    public static Result failure(ResultCodeEnum resultCodeEnum) {
        return Result.failure(resultCodeEnum.getCode(), resultCodeEnum.getMsg());
    }

    public static Result failure(ResultCodeEnum resultCodeEnum, String sysMsg) {
        return Result.failure(resultCodeEnum.getCode(), resultCodeEnum.getMsg(), sysMsg);
    }

    public static Result failure(String code, String msg) {
        return Result.failure(code, msg, null);
    }

    public static Result failure(BizException e) {
        return Result.failure(e.getCode(), e.getMsg(), e.getMessage());
    }

    public static Result failure(String code, String msg, String sysMsg) {
        Result result = new Result();
        result.setCode(code);
        result.setSuccess(false);
        result.setMsg(msg);
        result.setErrorMsg(sysMsg);
        return result;
    }

    public static Result failure(BindingResult result) {
        if (null != result && result.hasErrors()) {
            Map<String, String> map = new HashMap();
            List<FieldError> list = result.getFieldErrors();
            Iterator var3 = list.iterator();

            while (var3.hasNext()) {
                FieldError error = (FieldError) var3.next();
                map.put(error.getField(), error.getDefaultMessage());
            }

            return failure(ResultCodeEnum.PARAM_ERROR.getCode(), ResultCodeEnum.PARAM_ERROR.getMsg(), map.toString());
        } else {
            return failure(ResultCodeEnum.INTERNAL_SERVER_ERROR.getCode(), ResultCodeEnum.INTERNAL_SERVER_ERROR.getMsg());
        }
    }

}
