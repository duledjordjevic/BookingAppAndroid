package com.example.bookingapplication.helpers;

import android.content.Context;
import android.widget.Button;

import com.example.bookingapplication.model.DateRange;

import java.time.LocalDate;
import java.util.Calendar;

public class DatePickerDialogHelper {

    private Button startDateBtn;
    private Button endDateBtn;
    private DateRange dateRange;

    public DatePickerDialogHelper(Button startDateBtn, Button endDateBtn, DateRange dateRange) {
        this.startDateBtn = startDateBtn;
        this.endDateBtn = endDateBtn;
        this.dateRange = dateRange;
    }

    public void showDatePickerDialog(Context context, String datePicker) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        android.app.DatePickerDialog datePickerDialog = new android.app.DatePickerDialog(
                context,
                new android.app.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
                        String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        if (datePicker.equals("startDate")){
                            startDateBtn.setText(selectedDate);
                            dateRange.setStartDate(LocalDate.of(year, month + 1, dayOfMonth));
                        }else{
                            endDateBtn.setText(selectedDate);
                            dateRange.setEndDate(LocalDate.of(year, month + 1, dayOfMonth));
                        }

                    }
                },
                year, month, day);

        datePickerDialog.show();
    }
}
