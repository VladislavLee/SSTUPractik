package com.sstu.practic.spring.utils.entities;

import javafx.event.EventHandler;
import javafx.event.EventType;
import lombok.Data;

@Data
public class EventPair{
    private EventType eventType;
    private EventHandler eventHandler;
}
