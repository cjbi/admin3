package tech.wetech.admin.web.exception;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

import tech.wetech.admin.model.system.BizException;
import tech.wetech.admin.web.dto.JsonResult;

@ControllerAdvice
public class DefaultExceptionHandler{
    /**
     * 没有权限 异常
     * <p/>
     * 后续根据不同的需求定制即可
     */
    @ExceptionHandler({ UnauthorizedException.class })
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
    @ExceptionHandler({ BizException.class })
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    public JsonResult processBizException(NativeWebRequest request, BizException e) {
        JsonResult json = new JsonResult();
        json.setStatus(HttpStatus.NOT_IMPLEMENTED.toString());
        json.setMsg(e.getMessage());
        json.setSuccess(false);
        return json;
    }

    /**
     * 参数校验异常
     * 
     * @param request
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler({ BindException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public JsonResult processBindException(HttpServletRequest request, HttpServletResponse response, BindException e, BindingResult br) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        JsonResult json = new JsonResult();
        StringBuilder msg = new StringBuilder();
        List<ObjectError> allErrors = br.getAllErrors();
        for (ObjectError error : allErrors) {
            msg.append(error.getDefaultMessage());
            msg.append(",");
        }
        if (msg.length() > 0) {
            msg.deleteCharAt(msg.length() - 1);
        }
        json.setStatus(HttpStatus.BAD_REQUEST.toString());
        json.setMsg(msg.toString());
        json.setSuccess(false);
        json.setObj(br.getAllErrors());
        return json;
    }

}
