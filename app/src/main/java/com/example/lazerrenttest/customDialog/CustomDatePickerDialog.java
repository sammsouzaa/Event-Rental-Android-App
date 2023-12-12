package com.example.lazerrenttest.customDialog;

import static java.security.AccessController.getContext;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomDatePickerDialog extends DatePickerDialog {

    private List<Date> unavailableDates;
    private Context context;
    private boolean toastAlreadyShown = false;

    public CustomDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener listener, int year, int month, int day, List<Date> unavailableDates) {
        super(context, listener, year, month, day);
        this.unavailableDates = unavailableDates;
        this.context = context;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int dayOfMonth) {
        if (!dateIsUnavailable(year, month, dayOfMonth)) {
            // Call the super method to update the selected date
            super.onDateChanged(view, year, month, dayOfMonth);
            // Reset the flag when a valid date is selected
            toastAlreadyShown = false;
        }
    }

    private boolean dateIsUnavailable(int year, int month, int dayOfMonth) {
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(year, month, dayOfMonth);

        for (Date date : unavailableDates) {
            Calendar disabledDate = Calendar.getInstance();
            disabledDate.setTime(date);
            if (disabledDate.get(Calendar.YEAR) == year && disabledDate.get(Calendar.MONTH) == month && disabledDate.get(Calendar.DAY_OF_MONTH) == dayOfMonth) {
                // Show Toast indicating that the selected date is unavailable
                if (!toastAlreadyShown) {
                    Toast.makeText(context, "A data selecionada está indisponível.", Toast.LENGTH_SHORT).show();
                    toastAlreadyShown = true;
                }
                // Restore the previous selection to prevent setting the disabled date
                super.updateDate(year, month, dayOfMonth);
                return true;
            }
        }

        return false;
    }
}
