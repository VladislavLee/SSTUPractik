package com.sstu.practic.spring.aspects;

import com.sstu.practic.spring.components.auth.LoginComponent;
import com.sstu.practic.spring.services.security.SecurityContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class GetSceneAspect {
    @Autowired
    private LoginComponent loginComponent;
    @Autowired
    private SecurityContext securityContext;

    @Around("@annotation(com.sstu.practic.spring.annotations.Authenticated)")
    public Object logAction(ProceedingJoinPoint pjp) throws Throwable {
        if(securityContext.isAuthenticated()) return pjp.proceed();
        return loginComponent.getScene();
    }
}
