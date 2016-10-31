package com.dexels.navajo.server.enterprise.scheduler;

public abstract class TriggerException extends Exception {
    private static final long serialVersionUID = -1111531209602380903L;

    public TriggerException(String msg) {
        super(msg);
    }

    public TriggerException(String string, Throwable parent) {
        super(string, parent);
    }

}