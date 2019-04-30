package br.com.softmotions.apptodolist.dao;

import android.util.Log;

import br.com.softmotions.apptodolist.MyApplication;
import br.com.softmotions.apptodolist.model.TodoNode;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class TodoDAO implements IDao<TodoNode> {
    public static final String TAG = "LogX_TodoDao";

    private Realm realm = MyApplication.REALM;

    @Override
    public void setObject(TodoNode object) {
        realm.beginTransaction();

        TodoNode todoNode = realm.createObject(TodoNode.class, idGenerator(TodoNode.class, "id"));

        todoNode.setTodo(object.getTodo());
        todoNode.setData(object.getData());
        todoNode.setHora(object.getHora());
        todoNode.setDataConclusion(object.getDataConclusion());
        todoNode.setHoraConclusion(object.getHoraConclusion());
        todoNode.setActive(object.isActive());

        realm.commitTransaction();
    }

    @Override
    public void deleteObject(TodoNode object) {
        //realm.beginTransaction();
        final TodoNode todoNode = realm.where(TodoNode.class).equalTo("id", object.getId()).findFirst();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                todoNode.deleteFromRealm();
            }
        });
    }

    @Override
    public void deleteObjects() {
        final RealmResults<TodoNode> results = realm.where(TodoNode.class).findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    @Override
    public void deleteObjectsActive() {
        final RealmResults<TodoNode> results = realm.where(TodoNode.class).equalTo("active", true).findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    @Override
    public void deleteObjectsCompleted() {
        final RealmResults<TodoNode> results = realm.where(TodoNode.class).equalTo("active", false).findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                results.deleteAllFromRealm();
            }
        });
    }

    @Override
    public void equalsObject(final TodoNode objeto) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(objeto);
            }
        });
    }

    @Override
    public RealmResults<TodoNode> getActiveObject() {
        //RealmResults<TodoNode> results = realm.where(TodoNode.class).findAllSorted("id", Sort.DESCENDING);
        return realm.where(TodoNode.class).equalTo("active", true).findAllSorted("id", Sort.DESCENDING);
    }

    @Override
    public RealmResults<TodoNode> getFinishObject() {
        return realm.where(TodoNode.class).equalTo("active", false).findAllSorted("id", Sort.DESCENDING);
    }

    @Override
    public TodoNode getObject(int id) {
        return realm.where(TodoNode.class).equalTo("id", id).findFirst();
    }

    @Override
    public int idGenerator(Class aClass, String field) {
        try {
            return realm.where(aClass).max(field).intValue() + 1;
        } catch (Exception e) {
            Log.d(TAG, String.valueOf(e));
            return 0;
        }
    }
}
