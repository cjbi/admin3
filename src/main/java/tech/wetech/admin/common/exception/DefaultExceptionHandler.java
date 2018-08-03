package tech.wetech.admin.common.exception;

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
import tech.wetech.admin.common.base.Result;
import tech.wetech.admin.common.base.ResultCodeEnum;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class DefaultExceptionHandler {

    public static Logger LOGGER = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler({Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<Object> processThrowable(HttpServletRequest request, Throwable e) {
        LOGGER.error("execute methond exception error.url is {}", request.getRequestURI(), e);
        return Result.failure(ResultCodeEnum.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    /**
     * 没有权限 异常
     * <p/>
     * 后续根据不同的需求定制即可
     *//*
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ModelAndView processUnauthenticatedException(NativeWebRequest request, UnauthorizedException e) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("exception", e);
        mv.setViewName("system/unauthorized");
        return mv;
    }*/

    @ResponseBody
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result processUnauthenticatedException(HttpServletRequest request, UnauthorizedException e) {
        LOGGER.error("execute methond exception error.url is {}", request.getRequestURI(), e);
        return Result.failure(ResultCodeEnum.UNAUTHORIZED, e.getMessage());
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
    public Result processBizException(HttpServletRequest request, BizException e) {
        LOGGER.error("execute methond exception error.url is {}", request.getRequestURI(), e);
        return Result.failure(e);
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
    public Result processBindException(HttpServletRequest request, BindException e, BindingResult br) {
        LOGGER.error("execute methond exception error.url is {}", request.getRequestURI(), e);
        return Result.failure(br);
    }

}
