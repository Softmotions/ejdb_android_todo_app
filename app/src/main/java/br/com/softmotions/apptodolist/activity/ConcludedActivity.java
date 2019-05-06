package br.com.softmotions.apptodolist.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import br.com.softmotions.apptodolist.R;
import br.com.softmotions.apptodolist.adapter.TodoNodeAdapter;
import br.com.softmotions.apptodolist.dao.TodoDAO;
import br.com.softmotions.apptodolist.model.TodoNode;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ConcludedActivity extends AppCompatActivity {

    private static final String TAG = "LogX_Concluded";
    @BindView(R.id.concluidas_rv_concluidas)
    RecyclerView rvConcluded;

    private TodoNodeAdapter todoNodeAdapter;
    private List<TodoNode> concludedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concluidas);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        concludedList = new LinkedList<>();

        rvConcluded.setLayoutManager(new LinearLayoutManager(this));
        rvConcluded.addItemDecoration(new DividerItemDecoration(ConcludedActivity.this, DividerItemDecoration.VERTICAL));
        rvConcluded.setItemAnimator(new DefaultItemAnimator());
        rvConcluded.setHasFixedSize(true);

        todoNodeAdapter = new TodoNodeAdapter(ConcludedActivity.this, concludedList, onItemClickListener(), onItemLongClickListener());
        rvConcluded.setAdapter(todoNodeAdapter);


    }

    public void atualizaLista() {
        //RealmResults<TodoNode> tarefas = MyApplication.REALM.where(TodoNode.class).findAllSorted("id", Sort.DESCENDING);
        concludedList.clear();
        concludedList.addAll(new TodoDAO().getFinishObject());
        todoNodeAdapter.notifyDataSetChanged();

        Log.d(TAG, "List todos: " + concludedList.toString());
    }

    @Override
    protected void onResume() {
        super.onResume();

        atualizaLista();
    }

    protected TodoNodeAdapter.OnItemClickListener onItemClickListener() {
        return new TodoNodeAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(View view, int position) {
                Intent intent = new Intent(ConcludedActivity.this, DetailActivity.class);
                intent.putExtra("index", concludedList.get(position).getId());
                startActivity(intent);
            }
        };
    }

    protected TodoNodeAdapter.OnItemLongClickListener onItemLongClickListener() {
        return new TodoNodeAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(final int position) {
                CharSequence[] options = new CharSequence[]{"View", "Delete", "Reactive"};

                AlertDialog.Builder builder = new AlertDialog.Builder(ConcludedActivity.this);
                builder.setTitle("Options todoNode");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(), "Clicou " + which, Toast.LENGTH_SHORT).show();
                        Intent intent;
                        long index = concludedList.get(position).getId();
                        switch (which) {
                            case 0:
                                intent = new Intent(ConcludedActivity.this, DetailActivity.class);
                                intent.putExtra("index", index);
                                startActivity(intent);
                                break;
                            case 1:
                                new AlertDialog.Builder(ConcludedActivity.this)
                                        .setTitle("Notice!")
                                        .setMessage("Do you want to delete todoNode " + concludedList.get(position).getTodo() + "?")
                                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                TodoNode todoNode = concludedList.get(position);
                                                new TodoDAO().deleteObject(todoNode);
                                                atualizaLista();
                                            }
                                        }).setNegativeButton("Não", null).show();
                                break;
                            case 2:
                                TodoNode todoNode = new TodoNode();
                                todoNode.setId(concludedList.get(position).getId());
                                todoNode.setTodo(concludedList.get(position).getTodo());
                                todoNode.setHour(concludedList.get(position).getHour());
                                todoNode.setData(concludedList.get(position).getData());
                                todoNode.setActive(true);
                                todoNode.setHourConclusion("Not completed");
                                todoNode.setDataConclusion("Not completed");

                                new TodoDAO().equalsObject(todoNode);

                                Toast.makeText(getApplicationContext(), "TodoNode reactivated successfully!", Toast.LENGTH_SHORT).show();

                                atualizaLista();
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
        getMenuInflater().inflate(R.menu.menu_concluidas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_excluir_concluidas:
                new TodoDAO().deleteObjectsCompleted();
                if (concludedList.size() > 0)
                    Toast.makeText(getApplicationContext(), "All completed todo deleted!", Toast.LENGTH_SHORT).show();
                atualizaLista();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
