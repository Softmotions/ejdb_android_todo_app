package com.softmotions.apptodolist.model;

public class Patch {
    private String op;
    private String path;
    private String value;

    public Patch() {
    }

    public Patch(String op, String path, String value) {
        this.op = op;
        this.path = path;
        this.value = value;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOp() {
        return op;
    }

    public String getPath() {
        return path;
    }

    public String getValue() {
        return value;
    }
}
