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

import java.io.ByteArrayOutputStream;
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
    public void equalsObject(final TodoNode object) {
        Collection<Patch> patchs = new LinkedList<Patch>();
        patchs.add(new Patch("replace", "/todo", object.getTodo()));
        patchs.add(new Patch("replace", "/data", object.getData()));
        patchs.add(new Patch("replace", "/hour", object.getHour()));
        patchs.add(new Patch("replace", "/dataConclusion", object.getDataConclusion()));
        patchs.add(new Patch("replace", "/hourConclusion", object.getHourConclusion()));
        patchs.add(new Patch("replace", "/active", Boolean.toString(object.isActive())));
        try {
            String patch = mapper.writeValueAsString(patchs);
            ejdb2.patch(TABLE_NAME, patch, object.getId());
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
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ejdb2.get(TABLE_NAME, id, os);
            os.flush();
            os.close();
            TodoNode node = mapper.readValue(os.toString(), TodoNode.class);
            node.setId(id);
            return node;
        } catch (IOException e) {
            Log.d(TAG, String.valueOf(e));
            return null;
        }
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
