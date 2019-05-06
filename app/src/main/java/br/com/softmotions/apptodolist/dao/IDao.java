package br.com.softmotions.apptodolist.dao;

import java.util.Collection;

public interface IDao<T> {
    public void setObject(T objeto);
    public void deleteObject(T objeto);
    public void deleteObjects();
    public void deleteObjectsActive();
    public void deleteObjectsCompleted();
    public void equalsObject(T objeto);
    public Collection<T> getActiveObject();
    public Collection<T> getFinishObject();
    public T getObject(int id);
}
