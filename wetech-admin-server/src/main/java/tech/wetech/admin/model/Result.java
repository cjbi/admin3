package tech.wetech.admin.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import tech.wetech.admin.model.enumeration.CommonResultStatus;

import java.io.Serializable;

/**
 * @author cjbi@outlook.com
 */
@Data
@Builder
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("是否成功")
    private boolean success;

    @ApiModelProperty("编码")
    private String code;

    @ApiModelProperty("消息")
    private String msg;

    @ApiModelProperty("数据")
    private T data;

    private static final Result EMPTY_SUCCESS_RESULT = Result.success(null);

    public static <T> Result<T> success() {
        return EMPTY_SUCCESS_RESULT;
    }

    public static <T> Result<T> success(T obj) {
        ResultBuilder<T> resultBuilder = new ResultBuilder<T>();
        return resultBuilder
                .data(obj)
                .code(CommonResultStatus.OK.getCode())
                .msg(CommonResultStatus.OK.getMsg())
                .success(true).build();
    }

    public static Result failure(ResultStatus resultStatus) {
        return Result.builder()
                .success(false)
                .code(resultStatus.getCode())
                .msg(resultStatus.getMsg()).build();
    }

    public static Result failure(ResultStatus resultStatus, Throwable e) {
        return Result.builder()
                .success(false)
                .data(e)
                .code(resultStatus.getCode())
                .msg(resultStatus.getMsg()).build();
    }

    public static Result failure(ResultStatus resultStatus, String message) {
        return Result.builder()
                .success(false)
                .code(resultStatus.getCode())
                .msg(message).build();
    }


}
