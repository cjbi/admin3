package tech.wetech.admin.web.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

import tech.wetech.admin.common.base.Result;
import tech.wetech.admin.common.base.ResultCodeEnum;
import tech.wetech.admin.model.system.BizException;

@ControllerAdvice
public class DefaultExceptionHandler {

    public static Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler({Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Object> processThrowable(HttpServletRequest request, Throwable e) {
        LOGGER.error("execute methond exception error.url is {}", request.getRequestURI(), e);
        return Result.Failure(ResultCodeEnum.InternalServerError, e.getMessage());
    }

    /**
     * 没有权限 异常
     * <p/>
     * 后续根据不同的需求定制即可
     */
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView processUnauthenticatedException(NativeWebRequest request, UnauthorizedException e) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("exception", e);
        mv.setViewName("system/unauthorized");
        return mv;
    }

    /**
     * 服务异常
     *
     * @param request
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler({BizException.class})
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public Result processBizException(NativeWebRequest request, BizException e) {
        return Result.Failure(e.getCode(), e.getMsg(), e.getMessage());
    }

    /**
     * 参数校验异常
     *
     * @param request
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler({BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result processBindException(HttpServletRequest request, HttpServletResponse response, BindException e, BindingResult br) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        StringBuilder msg = new StringBuilder();
        br.getAllErrors().forEach(error -> {
            msg.append(error.getDefaultMessage());
            msg.append(",");
        });
        if (msg.length() > 0) {
            msg.deleteCharAt(msg.length() - 1);
        }
        return Result.Failure(ResultCodeEnum.BadRequest.getCode(), msg.toString(), e.getMessage());
    }

}
