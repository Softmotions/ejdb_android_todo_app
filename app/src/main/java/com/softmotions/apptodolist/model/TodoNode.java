package com.softmotions.apptodolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TodoNode{
    private long id;
    private String todo;
    private String data;
    private String hour;
    private String dataCompletion;
    private String hourCompletion;
    private boolean active;

    public TodoNode() {
    }

    @JsonIgnore
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTodo() {
        return todo;
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDataCompletion() {
        return dataCompletion;
    }

    public void setDataCompletion(String dataCompletion) {
        this.dataCompletion = dataCompletion;
    }

    public String getHourCompletion() {
        return hourCompletion;
    }

    public void setHourCompletion(String hourCompletion) {
        this.hourCompletion = hourCompletion;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "TodoNode{" +
                "id=" + id +
                ", todo='" + todo + '\'' +
                ", data='" + data + '\'' +
                ", hour='" + hour + '\'' +
                ", dataCompletion='" + dataCompletion + '\'' +
                ", hourCompletion='" + hourCompletion + '\'' +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TodoNode todoNode1 = (TodoNode) o;

        if (id != todoNode1.id) return false;
        if (active != todoNode1.active) return false;
        if (todo != null ? !todo.equals(todoNode1.todo) : todoNode1.todo != null) return false;
        if (data != null ? !data.equals(todoNode1.data) : todoNode1.data != null) return false;
        if (hour != null ? !hour.equals(todoNode1.hour) : todoNode1.hour != null) return false;
        if (dataCompletion != null ? !dataCompletion.equals(todoNode1.dataCompletion) : todoNode1.dataCompletion != null)
            return false;
        return hourCompletion != null ? hourCompletion.equals(todoNode1.hourCompletion) : todoNode1.hourCompletion == null;

    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (todo != null ? todo.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (hour != null ? hour.hashCode() : 0);
        result = 31 * result + (dataCompletion != null ? dataCompletion.hashCode() : 0);
        result = 31 * result + (hourCompletion != null ? hourCompletion.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        return result;
    }
}