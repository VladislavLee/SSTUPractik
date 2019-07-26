package com.sstu.practic.spring.bpp;

import com.sstu.practic.spring.annotations.HandleEvent;
import com.sstu.practic.spring.annotations.JavaFxComponent;
import com.sstu.practic.spring.components.FxComponent;
import com.sstu.practic.spring.utils.entities.EventPair;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import lombok.SneakyThrows;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JavaFxComponentBeanPostProcessor implements BeanPostProcessor {

    @Override
    @SneakyThrows
    public Object postProcessBeforeInitialization(Object bean, String beanName){
        if(bean.getClass().isAnnotationPresent(JavaFxComponent.class)){
            JavaFxComponent annotation = bean.getClass().getAnnotation(JavaFxComponent.class);

            Parent root = FXMLLoader.load(Thread.currentThread().getClass().getResource(annotation.path()));
            Scene scene = new Scene(root, annotation.width(), annotation.height());

            ((FxComponent)bean).setScene(scene);

            List<Method> methods = Arrays.stream(bean.getClass().getDeclaredMethods())
                    .filter((x)->x.isAnnotationPresent(HandleEvent.class))
                    .collect(Collectors.toList());

            methods.stream()
                    .forEach((x)->hangEvent(
                            x.getAnnotation(HandleEvent.class).nodeName(),
                            (EventPair) ReflectionUtils.invokeMethod(x,bean),
                            (FxComponent) bean));
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName){
        return bean;
    }


    private void hangEvent(String nodeName ,EventPair eventPair, FxComponent bean){
        bean.injectEventHandler(eventPair.getEventType(),nodeName,eventPair.getEventHandler());
    }
}
