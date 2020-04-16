package tech.wetech.admin.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.wetech.admin.model.Result;
import tech.wetech.admin.model.enumeration.CommonResultStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cjbi@outlook.com
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Object> handleThrowable(HttpServletRequest request, Throwable e) {
        log.error("execute method exception error.url is {}", request.getRequestURI(), e);
        return Result.failure(CommonResultStatus.INTERNAL_SERVER_ERROR, e);
    }

    /**
     * 服务异常
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({BusinessException.class})
    public Result handleBusinessException(HttpServletRequest request, BusinessException e) {
        log.error("execute method exception error.url is {}", request.getRequestURI(), e);
        return Result.failure(e.getStatus(), e.getMessage());
    }

    /**
     * 参数校验异常
     * <p>使用 @Valid 注解，方法上加@RequestBody注解修饰的方法未校验通过会抛MethodArgumentNotValidException，否则抛BindException。
     * <p>使用 @Validated 注解，未校验通过会抛ConstraintViolationException
     * <p>关于 @Valid和@Validated注解的区别:
     * <p> 这两个注解都是实现JSR-303规范，不同的是@Validated是spring的注解支持groups以及可以用在spring mvc处理器的方法级别入参验证 ，@Valid是Javax提供的注解，可以支持多个bean嵌套验证。
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class, ConstraintViolationException.class})
    public Result handleJSR303Exception(HttpServletRequest request, Exception e) {
        log.error("execute method exception error.url is {}", request.getRequestURI(), e);
        BindingResult br = null;
        Result result = Result.builder()
                .success(false)
                .code(CommonResultStatus.PARAM_ERROR.getCode())
                .message(CommonResultStatus.PARAM_ERROR.getMessage())
                .build();

        if (e instanceof BindException) {
            br = ((BindException) e).getBindingResult();
        }
        if (e instanceof MethodArgumentNotValidException) {
            br = ((MethodArgumentNotValidException) e).getBindingResult();
        }
        if (br != null) {
            result.setResult(
                    br.getFieldErrors().stream()
                            .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (oldValue, newValue) -> oldValue.concat(",").concat(newValue)))
            );
            result.setMessage(
                    br.getFieldErrors().stream()
                            .map(f -> f.getField().concat(f.getDefaultMessage()))
                            .collect(Collectors.joining(","))
            );
            return result;
        }
        if (e instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) e).getConstraintViolations();
            result.setResult(
                    constraintViolations.stream().collect(Collectors.toMap(ConstraintViolation::getPropertyPath, ConstraintViolation::getMessage))
            );
            result.setMessage(e.getMessage());
        }
        return result;
    }

    /**
     * 捕获sql语法异常，原生的Message不适合中文环境
     *
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    public Result handleSQLSyntaxErrorException(HttpServletRequest request, BadSqlGrammarException e) {
        String message = e.getSQLException().getMessage();
        Map<String, String> regexMap = new HashMap<>();
        //数据表不存在
        regexMap.put("Table '(\\S+)' doesn't exist", "表($1)不存在");
        //唯一键
        regexMap.put("Duplicate entry '(\\S+)' for key '(\\S+)'", "$1已经存在了");
        for (Map.Entry<String, String> entry : regexMap.entrySet()) {
            if (message.matches(entry.getKey())) {
                message = message.replaceAll(entry.getKey(), entry.getValue());
                break;
            }
        }
        log.warn(">>> Handle SQLSyntaxErrorException, url is {}, message is {}", request.getRequestURI(), message);
        return Result.failure(CommonResultStatus.INTERNAL_SERVER_ERROR, message);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public Result handleUnauthorizedException(HttpServletRequest request, UnauthorizedException e) {
        Map<String, String> regexMap = new HashMap<>();
        regexMap.put("Subject does not have permission (\\S+)", "操作失败，用户不存在权限$1，请检查权限配置");
        String message = e.getMessage();
        for (Map.Entry<String, String> entry : regexMap.entrySet()) {
            if (message.matches(entry.getKey())) {
                message = message.replaceAll(entry.getKey(), entry.getValue());
                break;
            }
        }
        log.error("execute method exception error.url is {}", request.getRequestURI(), e);
        return Result.failure(CommonResultStatus.UNAUTHORIZED, message);
    }

}
