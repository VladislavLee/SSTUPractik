package com.sstu.practic.spring.components;

import com.sstu.practic.spring.annotations.Authenticated;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.Scene;

public abstract class FxComponent {
    protected Scene scene;

    @Authenticated
    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void injectEventHandler(EventType eventType, String nodeName, EventHandler eventHandler) {
        Node node = scene.lookup("#"+nodeName);
        node.addEventHandler(eventType, eventHandler);
    }

}
