package br.com.softmotions.apptodolist.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TodoNode extends RealmObject {
    @PrimaryKey
    private int id;
    private String todo;
    private String data;
    private String hora;
    private String dataConclusion;
    private String horaConclusion;
    private boolean active;

    public TodoNode() {
    }

    public TodoNode(int id, String todo, String data, String hora, String dataConclusion, String horaConclusion, boolean active) {
        this.id = id;
        this.todo = todo;
        this.data = data;
        this.hora = hora;
        this.dataConclusion = dataConclusion;
        this.horaConclusion = horaConclusion;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDataConclusion() {
        return dataConclusion;
    }

    public void setDataConclusion(String dataConclusion) {
        this.dataConclusion = dataConclusion;
    }

    public String getHoraConclusion() {
        return horaConclusion;
    }

    public void setHoraConclusion(String horaConclusion) {
        this.horaConclusion = horaConclusion;
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
                ", hora='" + hora + '\'' +
                ", dataConclusion='" + dataConclusion + '\'' +
                ", horaConclusion='" + horaConclusion + '\'' +
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
        if (hora != null ? !hora.equals(todoNode1.hora) : todoNode1.hora != null) return false;
        if (dataConclusion != null ? !dataConclusion.equals(todoNode1.dataConclusion) : todoNode1.dataConclusion != null)
            return false;
        return horaConclusion != null ? horaConclusion.equals(todoNode1.horaConclusion) : todoNode1.horaConclusion == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (todo != null ? todo.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + (hora != null ? hora.hashCode() : 0);
        result = 31 * result + (dataConclusion != null ? dataConclusion.hashCode() : 0);
        result = 31 * result + (horaConclusion != null ? horaConclusion.hashCode() : 0);
        result = 31 * result + (active ? 1 : 0);
        return result;
    }
}