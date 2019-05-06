package br.com.softmotions.apptodolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class TodoNode{
    private long id;
    private String todo;
    private String data;
    private String hour;
    private String dataConclusion;
    private String hourConclusion;
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

    public String getDataConclusion() {
        return dataConclusion;
    }

    public void setDataConclusion(String dataConclusion) {
        this.dataConclusion = dataConclusion;
    }

    public String getHourConclusion() {
        return hourConclusion;
    }

    public void setHourConclusion(String hourConclusion) {
        this.hourConclusion = hourConclusion;
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
                ", dataConclusion='" + dataConclusion + '\'' +
                ", hourConclusion='" + hourConclusion + '\'' +
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
        if (dataConclusion != null ? !dataConclusion.equals(todoNode1.dataConclusion) : todoNode1.dataConclusion != null)
            return false;
        return hourConclusion != null ? hourConclusion.equals(todoNode1.hourConclusion) : todoNode1.hourConclusion == null;

    }

    @Override
    public int hashCode() {
        int result = (int) id;
        result = 31 * result + (todo != null ? todo.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (hour != null ? hour.hashCode() : 0);
        result = 31 * result + (dataConclusion != null ? dataConclusion.hashCode() : 0);
        result = 31 * result + (hourConclusion != null ? hourConclusion.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        return result;
    }
}