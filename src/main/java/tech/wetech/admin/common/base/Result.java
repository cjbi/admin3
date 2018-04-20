package tech.wetech.admin.common.base;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import tech.wetech.admin.model.system.BizException;

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

    private String sysMsg;

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

    public String getSysMsg() {
        return sysMsg;
    }

    public void setSysMsg(String sysMsg) {
        this.sysMsg = sysMsg;
    }

    public static Result Success() {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(ResultCodeEnum.OK.getCode());
        result.setMsg(ResultCodeEnum.OK.getMsg());
        result.setData(new HashMap<>(1));
        return result;
    }

    public static <T> Result Success(T obj) {
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

    public static Result Failure(ResultCodeEnum resultCodeEnum) {
        return Result.Failure(resultCodeEnum.getCode(), resultCodeEnum.getMsg());
    }

    public static Result Failure(ResultCodeEnum resultCodeEnum, String sysMsg) {
        return Result.Failure(resultCodeEnum.getCode(), resultCodeEnum.getMsg(), sysMsg);
    }

    public static Result Failure(String code, String msg) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result Failure(String code, String msg, String sysMsg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setSysMsg(sysMsg);
        return result;
    }

    public static Result Failure(BizException e) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(e.getCode());
        result.setMsg(e.getMsg());
        result.setSysMsg(e.getMessage());
        return result;
    }

    public static Result Failure(BindingResult result) {
        if (null != result && result.hasErrors()) {
            Map<String, String> map = new HashMap();
            List<FieldError> list = result.getFieldErrors();
            Iterator var3 = list.iterator();

            while (var3.hasNext()) {
                FieldError error = (FieldError) var3.next();
                map.put(error.getField(), error.getDefaultMessage());
            }

            return Failure(ResultCodeEnum.ParamError.getCode(), ResultCodeEnum.ParamError.getMsg(), map.toString());
        } else {
            return Failure(ResultCodeEnum.InternalServerError.getCode(), ResultCodeEnum.InternalServerError.getMsg());
        }
    }

}
