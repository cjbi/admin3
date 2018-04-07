package tech.wetech.admin.common.base;

import tech.wetech.admin.model.system.BizException;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author cjbi
 */
public class Result<T> implements Serializable {

    private boolean success;

    private String code;

    private String msg;

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

    public static Result Failure(String code, String msg) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static Result Failure(BizException e) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(e.getCode());
        result.setMsg(e.getMsg());
        return result;
    }

}
