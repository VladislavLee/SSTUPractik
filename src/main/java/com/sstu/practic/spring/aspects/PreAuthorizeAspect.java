package com.sstu.practic.spring.aspects;

import com.sstu.practic.spring.annotations.PreAuthorize;
import com.sstu.practic.spring.components.MainComponent;
import com.sstu.practic.spring.services.security.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
public class PreAuthorizeAspect {
    @Autowired
    private MainComponent mainComponent;
    @Autowired
    private SecurityContext securityContext;

    @Around("@annotation(com.sstu.practic.spring.annotations.PreAuthorize)")
    public Object logAction(ProceedingJoinPoint pjp) throws Throwable {
        return ((MethodSignature) pjp.getSignature())
                .getMethod()
                .getAnnotation(PreAuthorize.class)
                .value()
                .equals(securityContext.getUser().getVcRole()) ? pjp.proceed() : mainComponent.getScene();
    }
}
