package com.hlife.framework.aop;

import com.hlife.framework.base.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class ControllerAspect {
    // 第一个 * 返回值 后边是包名，类名 方法名
    @Pointcut(value = "execution(public * com.hlife.*.*.controller.*.*(..))")
    private void point() {
        log.debug("point===>");
    }

    @Around("point()")
    public ResultVO handlerControllerMethod(ProceedingJoinPoint joinPoint) {
        log.debug("handlerControllerMethod===>");
        try {
            return (ResultVO) joinPoint.proceed();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultVO(e);
        } catch (Throwable th) {
            th.printStackTrace();
            return new ResultVO(th);
        }
    }
}
