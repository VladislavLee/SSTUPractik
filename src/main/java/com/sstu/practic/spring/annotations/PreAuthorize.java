package com.sstu.practic.spring.annotations;

import com.sstu.practic.spring.services.security.entites.Role;
import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
@Component
public @interface PreAuthorize {
    Role value();
}
