package com.softmotions.apptodolist.activity;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.softmotions.apptodolist.R;
import com.softmotions.apptodolist.dao.TodoDAO;
import com.softmotions.apptodolist.fragment.DatePickerFragment;
import com.softmotions.apptodolist.fragment.TimePickerFragment;
import com.softmotions.apptodolist.model.TodoNode;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditActivity extends AppCompatActivity {

    public static final String TAG = "LogX_Edit";

    TodoNode todoNode;
    @BindView(R.id.edit_todo)
    EditText etTodo;
    @BindView(R.id.edit_hour)
    EditText etHour;
    @BindView(R.id.edit_data)
    EditText etData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        todoNode = new TodoDAO().getObject(intent.getLongExtra("index", 0));
        Log.d(TAG, todoNode.toString());

        etTodo.setText(todoNode.getTodo());
        etTodo.setSelection(todoNode.getTodo().length());

        etHour.setFocusable(false);
        if (!todoNode.getHour().equals("")) {
            etHour.setText(todoNode.getHour());
        } else {
            etHour.setText(getResources().getText(R.string.not_defined));
        }

        etData.setFocusable(false);
        if (!todoNode.getData().equals("")) {
            etData.setText(todoNode.getData());
        } else {
            etData.setText(getResources().getText(R.string.not_defined));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        TodoNode todoNodeEdited = new TodoNode();
        todoNodeEdited.setId(todoNode.getId());
        todoNodeEdited.setTodo(etTodo.getText().toString());
        todoNodeEdited.setHour(etHour.getText().toString());
        todoNodeEdited.setData(etData.getText().toString());
        todoNodeEdited.setHourCompletion(todoNode.getHourCompletion());
        todoNodeEdited.setDataCompletion(todoNode.getDataCompletion());
        todoNodeEdited.setActive(todoNode.isActive());

        switch (item.getItemId()) {
            case android.R.id.home:
                if (todoNodeEdited.hashCode() == todoNode.hashCode()) {
                    finish();
                    return true;
                } else {
                    new AlertDialog.Builder(EditActivity.this)
                            .setTitle("Attention!")
                            .setMessage("Do you want to discard changes?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            }).setNegativeButton("No", null).show();
                }
                return true;
            case R.id.action_save_item:
                //saves item
                Log.d(TAG, "TodoNode edited: " + todoNodeEdited.toString());
                new TodoDAO().updateObject(todoNodeEdited);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.edit_hour, R.id.edit_data})
    public void onClick(View view) {
        DialogFragment newFragment;
        switch (view.getId()) {
            case R.id.edit_hour:
                //flame fragment time picker
                newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(),"TimePicker");
                break;
            case R.id.edit_data:
                //flame fragment date picker
                newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(),"Date Picker");
                break;
        }
    }
}
