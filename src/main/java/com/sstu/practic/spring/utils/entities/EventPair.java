package com.sstu.practic.spring.utils.entities;

import javafx.event.EventHandler;
import javafx.event.EventType;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventPair{
    private EventType eventType;
    private EventHandler eventHandler;
}
