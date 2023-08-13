package com.example.demofx.Utils.Events;

import java.util.EventListener;
import java.util.EventObject;

public class EndWindowInitEvent extends EventObject {

    public EndWindowInitEvent(Object source) {
        super(source);
    }

    public EndWindowInitEvent() {
        super("");
    }
}

