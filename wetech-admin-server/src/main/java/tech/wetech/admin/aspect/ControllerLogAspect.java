package tech.wetech.admin.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Controller层日志切面
 * @author cjbi
 */
@Aspect
@Component
@Slf4j
public class ControllerLogAspect {
    /**
     * 切入点： 所有Controller类的public方法，忽略@LogIgnore注解的方法
     */
    @Pointcut("execution(public * tech.wetech.admin..*Controller.*(..))&&!@annotation(tech.wetech.admin.aspect.LogIgnore)")
    public void controllerLogPointcut() {
    }

    @Around("controllerLogPointcut()")
    public Object aroundMethod(ProceedingJoinPoint point) throws Throwable {
        long time = System.currentTimeMillis();
        Signature signature = point.getSignature();
        try {
            log.info(">>> Begin execute {}, args: {}", signature, point.getArgs());
            Object object = point.proceed();
            log.info("<<< End execute {} in {} ms, return: {}", signature, System.currentTimeMillis() - time, object);
            return object;
        } catch (Throwable e) {
            log.warn(">>> Execute {} has occurred exception: {}", signature, e.toString());
            throw e;
        }
    }

}
