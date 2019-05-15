package com.softmotions.apptodolist.dao;

import android.util.Log;

import com.softmotions.apptodolist.MyApplication;
import com.softmotions.apptodolist.model.Patch;
import com.softmotions.apptodolist.model.TodoNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softmotions.ejdb2.EJDB2;
import com.softmotions.ejdb2.JQL;
import com.softmotions.ejdb2.JQLCallback;

import java.io.IOException;
import java.util.*;

public class TodoDAO implements IDao<TodoNode> {

    public static final String TAG = "LogX_TodoDao";
    public static final String COLLECTION = "todos";

    private EJDB2 ejdb2 = MyApplication.ejdb2;
    private ObjectMapper mapper = MyApplication.mapper;

    @Override
    public void setObject(TodoNode todo) {
        ejdb2.put(COLLECTION, mapper.valueToTree(todo).toString());
    }

    @Override
    public void deleteObject(TodoNode object) {
        ejdb2.del(COLLECTION, object.getId());
    }

    @Override
    public void deleteObjects() {
        ejdb2.removeCollection(COLLECTION);
    }

    @Override
    public void deleteObjectsActive() {
        Collection<TodoNode> todos = getTodoNodes("/[active=:?]", "true");
        for (TodoNode todo : todos) {
            ejdb2.del(COLLECTION, todo.getId());
        }
    }

    @Override
    public void deleteObjectsCompleted() {
        Collection<TodoNode> todos = getTodoNodes("/[active=:?]", "false");
        for (TodoNode todo : todos) {
            ejdb2.del(COLLECTION, todo.getId());
        }
    }

    @Override
    public void updateObject(final TodoNode object) {
        // Used JSON patch for demo
        // Can be replaced by single call
        // ejdb2.put(COLLECTION, mapper.writeValueAsString(object), object.getId());
        Collection<Patch> patchs = new LinkedList<Patch>();
        patchs.add(new Patch("replace", "/todo", object.getTodo()));
        patchs.add(new Patch("replace", "/data", object.getData()));
        patchs.add(new Patch("replace", "/hour", object.getHour()));
        patchs.add(new Patch("replace", "/dataCompletion", object.getDataCompletion()));
        patchs.add(new Patch("replace", "/hourCompletion", object.getHourCompletion()));
        patchs.add(new Patch("replace", "/active", Boolean.toString(object.isActive())));
        try {
            String patch = mapper.writeValueAsString(patchs);
            ejdb2.patch(COLLECTION, patch, object.getId());
        } catch (JsonProcessingException e) {
            Log.d(TAG, String.valueOf(e));
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
    public TodoNode getObject(long id) {
        try {
            TodoNode node = mapper.readValue(ejdb2.getAsString(COLLECTION, id), TodoNode.class);
            node.setId(id);
            return node;
        } catch (IOException e) {
            Log.d(TAG, String.valueOf(e));
            return null;
        }
    }

    private Collection<TodoNode> getTodoNodes(String query, String value) {
        final Map<Long, String> results = new LinkedHashMap<>();
        JQL q = ejdb2.createQuery(query, COLLECTION).setString(0, value);
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
