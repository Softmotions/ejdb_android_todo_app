package br.com.softmotions.apptodolist.dao;

import io.realm.RealmModel;
import io.realm.RealmResults;

public interface IDao<T extends RealmModel> {
    public void setObject(T objeto);
    public void deleteObject(T objeto);
    public void deleteObjects();
    public void deleteObjectsActive();
    public void deleteObjectsCompleted();
    public void equalsObject(T objeto);
    public RealmResults<T> getActiveObject();
    public RealmResults<T> getFinishObject();
    public T getObject(int id);
    public int idGenerator(Class aClass, String field);
}
