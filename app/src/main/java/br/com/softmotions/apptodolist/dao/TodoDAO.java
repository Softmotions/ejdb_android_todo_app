package br.com.softmotions.apptodolist.dao;

import android.util.Log;

import br.com.softmotions.apptodolist.MyApplication;
import br.com.softmotions.apptodolist.model.Patch;
import br.com.softmotions.apptodolist.model.TodoNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softmotions.ejdb2.EJDB2;
import com.softmotions.ejdb2.JQL;
import com.softmotions.ejdb2.JQLCallback;

import java.io.IOException;
import java.util.*;

public class TodoDAO implements IDao<TodoNode> {

    public static final String TAG = "LogX_TodoDao";
    public static final String TABLE_NAME = "todos";

    private EJDB2 ejdb2 = MyApplication.ejdb2;
    private ObjectMapper mapper = MyApplication.mapper;

    @Override
    public void setObject(TodoNode todo) {
        ejdb2.put(TABLE_NAME, mapper.valueToTree(todo).toString());
    }

    @Override
    public void deleteObject(TodoNode object) {
        ejdb2.del(TABLE_NAME, object.getId());
    }

    @Override
    public void deleteObjects() {
        ejdb2.removeCollection(TABLE_NAME);
    }

    @Override
    public void deleteObjectsActive() {
        Collection<TodoNode> todos = getTodoNodes("/[active=:?]", "true");
        for (TodoNode todo : todos) {
            ejdb2.del(TABLE_NAME, todo.getId());
        }
    }

    @Override
    public void deleteObjectsCompleted() {
        Collection<TodoNode> todos = getTodoNodes("/[active=:?]", "false");
        for (TodoNode todo : todos) {
            ejdb2.del(TABLE_NAME, todo.getId());
        }
    }

    @Override
    public void equalsObject(final TodoNode objeto) {
        Collection<Patch> patch = new LinkedList<Patch>();
        patch.add(new Patch("replace", "todo", objeto.getTodo()));
        patch.add(new Patch("replace", "data", objeto.getData()));
        patch.add(new Patch("replace", "hour", objeto.getHora()));
        patch.add(new Patch("replace", "dataConclusion", objeto.getDataConclusion()));
        patch.add(new Patch("replace", "hourConclusion", objeto.getHoraConclusion()));
        patch.add(new Patch("replace", "active", Boolean.toString(objeto.isActive())));
        try {
            ejdb2.patch(TABLE_NAME, mapper.writeValueAsString(patch), objeto.getId());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<TodoNode> getActiveObject() {
        return getTodoNodes("/[active=:?]", "true");
    }

    @Override
    public Collection<TodoNode> getFinishObject() {
        return getTodoNodes("/[active=:?]", "false");
    }

    @Override
    public TodoNode getObject(int id) {
        Collection<TodoNode> todos = getTodoNodes("/[id=:?]", Integer.toString(id));
        return todos.iterator().next();
    }

    private Collection<TodoNode> getTodoNodes(String query, String value) {
        final Map<Long, String> results = new LinkedHashMap<>();
        JQL q = ejdb2.createQuery(query, TABLE_NAME).setString(0, value);
        q.execute(new JQLCallback() {
            @Override
            public long onRecord(long docId, String doc) {
                results.put(docId, doc);
                return 1;
            }
        });
        final Collection<TodoNode> todos = new LinkedList<>();
        try {
            for (Map.Entry<Long, String> entry : results.entrySet()) {
                TodoNode todo = mapper.readValue(entry.getValue(), TodoNode.class);
                todo.setId(entry.getKey());
                todos.add(todo);
            }
        } catch (IOException e) {
            Log.d(TAG, String.valueOf(e));
        }
        return todos;
    }
}
