package com.softmotions.apptodolist.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

import com.softmotions.apptodolist.R;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Use the current time as the default values for the time picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //Create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(),this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        EditText etHour = (EditText) getActivity().findViewById(R.id.edit_hour);

        String hour = String.valueOf(hourOfDay);
        String minuto = String.valueOf(minute);

        if (hour.length() == 1) {
            hour = "0" + hour;
        }

        if (minuto.length() == 1) {
            minuto = "0" + minuto;
        }

        etHour.setText(hour + ":" + minuto);
    }
}
