package com.dexels.navajo.tipi.swingclient.components;



public class MessageTableColumnDefinition {

    private String id;
    private String title;
    private boolean editable;

    public MessageTableColumnDefinition(String id, String title, boolean editable) {
        this.id = id;
        this.title = title;
        this.editable = editable;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isEditable() {
        return editable;
    }

}
