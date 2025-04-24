/*
 * Decompiled with CFR 0.152.
 */
package com.iassets.common.bo;

public class Message {
    protected String text;
    protected MESSAGE_TYPE type;

    public Message(String text, MESSAGE_TYPE type) {
        this.text = text;
        this.type = type;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void setType(MESSAGE_TYPE type) {
        this.type = type;
    }

    public MESSAGE_TYPE getType() {
        return this.type;
    }

    public static enum MESSAGE_TYPE {
        ERROR,
        WARNING,
        SUCCESS;

    }
}
