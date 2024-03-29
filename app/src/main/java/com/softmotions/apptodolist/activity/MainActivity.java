package com.softmotions.apptodolist.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.softmotions.apptodolist.adapter.TodoNodeAdapter;
import com.softmotions.apptodolist.model.TodoNode;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.softmotions.apptodolist.MyApplication;
import com.softmotions.apptodolist.R;
import com.softmotions.apptodolist.dao.TodoDAO;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements Validator.ValidationListener {

    private static final String TAG = "LogX_Main";
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @NotEmpty(message = MyApplication.MSG_VAZIO)
    @BindView(R.id.et_nova_tarefa_main)
    EditText etNewTodo;
    @BindView(R.id.rv_tarefas_main)
    RecyclerView rvTodo;

    private TodoNodeAdapter todoNodeAdapter;
    private Validator validator;
    private List<TodoNode> todoNodeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        validator = new Validator(this);
        validator.setValidationListener(this);

        todoNodeList = new LinkedList<>();

        rvTodo.setLayoutManager(new LinearLayoutManager(this));
        rvTodo.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
        rvTodo.setItemAnimator(new DefaultItemAnimator());
        rvTodo.setHasFixedSize(true);

        todoNodeAdapter = new TodoNodeAdapter(MainActivity.this, todoNodeList, onItemClickListener(), onItemLongClickListener());
        rvTodo.setAdapter(todoNodeAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });
    }

    public void updateList() {
        todoNodeList.clear();
        todoNodeList.addAll(new TodoDAO().getActiveObject());
        todoNodeAdapter.notifyDataSetChanged();

        Log.d(TAG, "List todos: " + todoNodeList.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateList();
    }

    protected TodoNodeAdapter.OnItemClickListener onItemClickListener() {
        return new TodoNodeAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("index", todoNodeList.get(position).getId());
                startActivity(intent);
            }
        };
    }

    protected TodoNodeAdapter.OnItemLongClickListener onItemLongClickListener() {
        return new TodoNodeAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(final int position) {
                CharSequence opcoes[] = new CharSequence[] {"View", "Change", "Delete", "Completed"};

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Options todoNode");
                builder.setItems(opcoes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent;
                        long index = todoNodeList.get(position).getId();
                        switch (which) {
                            case 0:
                                intent = new Intent(MainActivity.this, DetailActivity.class);
                                intent.putExtra("index", index);
                                startActivity(intent);
                                break;
                            case 1:
                                intent = new Intent(MainActivity.this, EditActivity.class);
                                intent.putExtra("index", index);
                                startActivity(intent);
                                break;
                            case 2:
                                new AlertDialog.Builder(MainActivity.this)
                                        .setTitle("Notice!")
                                        .setMessage("Do you want to a todoNode " + todoNodeList.get(position).getTodo() + "?")
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                TodoNode todoNode = todoNodeList.get(position);
                                                new TodoDAO().deleteObject(todoNode);
                                                updateList();
                                            }
                                        }).setNegativeButton("No", null).show();
                                break;
                            case 3:
                                SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
                                SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
                                Calendar calendar = Calendar.getInstance();
                                Date dataActual = calendar.getTime();

                                String hourActualString = hourFormat.format(dataActual);
                                String dataActualString = dataFormat.format(dataActual);

                                TodoNode todoNode = new TodoNode();
                                todoNode.setId(todoNodeList.get(position).getId());
                                todoNode.setTodo(todoNodeList.get(position).getTodo());
                                todoNode.setHour(todoNodeList.get(position).getHour());
                                todoNode.setData(todoNodeList.get(position).getData());
                                todoNode.setActive(false);
                                todoNode.setHourCompletion(hourActualString);
                                todoNode.setDataCompletion(dataActualString);

                                new TodoDAO().updateObject(todoNode);

                                    Toast.makeText(getApplicationContext(), "TodoNode successfully completed!", Toast.LENGTH_SHORT).show();

                                updateList();
                                break;
                        }

                    }
                }).show();

                return true;
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_delete_assets:
                new TodoDAO().deleteObjectsActive();
                Toast.makeText(getApplicationContext(), "All excluded todo!", Toast.LENGTH_SHORT).show();
                updateList();
                return true;
            case R.id.action_delete_todos:
                new TodoDAO().deleteObjects();
                Toast.makeText(getApplicationContext(), "All active todo deleted!", Toast.LENGTH_SHORT).show();
                updateList();
                return true;
            case R.id.action_delete_completed:
                startActivity(new Intent(MainActivity.this, CompletedActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onValidationSucceeded() {
        TodoNode todoNode = new TodoNode();
        todoNode.setTodo(etNewTodo.getText().toString());
        todoNode.setData("Not defined");
        todoNode.setHour("Not defined");
        todoNode.setDataCompletion("Not completed");
        todoNode.setHourCompletion("Not completed");
        todoNode.setActive(true);

        TodoDAO todoDAO = new TodoDAO();
        todoDAO.setObject(todoNode);
        etNewTodo.setText("");

        updateList();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}
