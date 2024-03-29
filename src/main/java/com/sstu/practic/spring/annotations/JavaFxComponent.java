package com.sstu.practic.spring.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(TYPE)
@Component
public @interface JavaFxComponent {

    String path();
    int width() default 1100;
    int height() default 720;
}
