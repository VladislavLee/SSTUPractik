package com.sstu.practic.spring.annotations;

import javafx.event.Event;
import javafx.event.EventType;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target(METHOD)
public @interface HandleEvent {
    String nodeName();
}
