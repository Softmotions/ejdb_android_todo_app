package br.com.softmotions.apptodolist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.softmotions.apptodolist.R;
import br.com.softmotions.apptodolist.dao.TodoDAO;
import br.com.softmotions.apptodolist.model.TodoNode;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String TAG = "LogX_Detalhe";

    long index;
    TodoNode todoNode;

    @BindView(R.id.detalhe_tv_tarefa)
    TextView tvTarefa;
    @BindView(R.id.detalhe_tv_hora)
    TextView tvHora;
    @BindView(R.id.detalhe_tv_data)
    TextView tvData;
    @BindView(R.id.detalhe_tv_titulo_conclusao)
    TextView tvTitleConclusion;
    @BindView(R.id.detalhe_tv_hora_conclusao)
    TextView tvHoraConclusion;
    @BindView(R.id.detalhe_tv_data_conclusao)
    TextView tvDataConclusion;
    @BindView(R.id.detalhe_card_conclusao)
    CardView cardConclusion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        index = intent.getLongExtra("index", 0);
        Log.d(TAG, "id: " + index);

        todoNode = new TodoDAO().getObject(index);

        updateFields();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateFields();
    }

    public void updateFields() {
        tvTarefa.setText(todoNode.getTodo());
        tvHora.setText(todoNode.getHour());
        tvData.setText(todoNode.getData());

        if (!todoNode.isActive()) {
            tvTitleConclusion.setVisibility(View.VISIBLE);
            cardConclusion.setVisibility(View.VISIBLE);

            tvHoraConclusion.setText(todoNode.getHourConclusion());
            tvDataConclusion.setText(todoNode.getDataConclusion());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalhe, menu);
        if (!todoNode.isActive()) {
            menu.removeItem(R.id.action_editar_item);
            menu.removeItem(R.id.action_concluir_item);

            MenuItem item = menu.findItem(R.id.action_excluir_item);
            item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_editar_item:
                Intent intent = new Intent(DetailActivity.this, EditActivity.class);
                intent.putExtra("index", todoNode.getId());
                startActivity(intent);
                return true;
            case R.id.action_excluir_item:
                new TodoDAO().deleteObject(todoNode);
                Toast.makeText(getApplicationContext(), "TodoNode successfully excluded!", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            case R.id.action_concluir_item:
                SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
                SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
                Calendar calendar = Calendar.getInstance();
                Date dataAtual = calendar.getTime();

                String hourActualString = hourFormat.format(dataAtual);
                String dataActualString = dataFormat.format(dataAtual);

                TodoNode todoNodeEdited = new TodoNode();
                todoNodeEdited.setId(todoNode.getId());
                todoNodeEdited.setTodo(todoNode.getTodo());
                todoNodeEdited.setHour(todoNode.getHour());
                todoNodeEdited.setData(todoNode.getData());
                todoNodeEdited.setActive(false);
                todoNodeEdited.setHourConclusion(hourActualString);
                todoNodeEdited.setDataConclusion(dataActualString);

                new TodoDAO().equalsObject(todoNodeEdited);

                Toast.makeText(getApplicationContext(), "TodoNode successfully completed!", Toast.LENGTH_SHORT).show();

                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
