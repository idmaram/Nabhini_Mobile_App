package com.app.nabhini;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.nabhini.BroadCast.AlarmsReceiver;
import com.app.nabhini.DB.DataBase;
import com.app.nabhini.Modle.Alarms;
import com.app.nabhini.utils.SharedPreferenceApp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class AddAlarmsActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_add;
    private EditText ed_title_alarm;
    private TextView tv_time, tv_date;
    private ImageView btn_time, btn_date, imageBack;

    private DataBase db;
    private int id_user;
    int hour, minute;

    private String timeSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarms);

        findView();

    }

    private void findView() {

        db = new DataBase(this);
        id_user = SharedPreferenceApp.getInstance(this).getNumber("idLogin", 0);

        btn_add = findViewById(R.id.btn_add);
        ed_title_alarm = findViewById(R.id.ed_title_alarm);
        tv_time = findViewById(R.id.tv_time);
        tv_date = findViewById(R.id.tv_date);
        btn_time = findViewById(R.id.btn_time);
        btn_date = findViewById(R.id.btn_date);
        imageBack = findViewById(R.id.imageBack);

        btn_add.setOnClickListener(this);
        btn_time.setOnClickListener(this);
        btn_date.setOnClickListener(this);
        imageBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imageBack:
                onBackPressed();
                break;

            case R.id.btn_add:
                if (validation()) {

                    String title_alarm = ed_title_alarm.getText().toString();
                    String time = tv_time.getText().toString();
                    String date = tv_date.getText().toString();

                    db.open();

                    boolean isDone = db.insertAlarms(new Alarms(id_user, title_alarm, date, time));

                    if (isDone) {
                        setAlarm(title_alarm, date, time);
                    } else {
                        Toast.makeText(this, "The Alert Has Not Been Added", Toast.LENGTH_SHORT).show();
                    }

                    db.close();


                }
                break;

            case R.id.btn_time:
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddAlarmsActivity.this, R.style.datepicker, (timePicker, i, i1) -> {
                    timeSelect = i + ":" + i1;
                    tv_time.setText(i + ":" + i1);
                }, hour, minute, true);
                timePickerDialog.show();
                break;

            case R.id.btn_date:
                showCalendarDialog();
                break;
        }
    }

    private boolean validation() {

        if (tv_time.getText().toString().equals("Choose The Time")) {
            Toast.makeText(this, "Please Choose The Time", Toast.LENGTH_LONG).show();

            return false;

        }
        if (tv_date.getText().toString().equals("Choose A Date")) {
            Toast.makeText(this, "Please Choose A Date", Toast.LENGTH_LONG).show();

            return false;

        } else if (!ValidationEmptyInput(ed_title_alarm)) {
            Toast.makeText(this, "Title Alarm is Empty", Toast.LENGTH_LONG).show();
            return false;

        } else {
            return true;

        }
    }

    private void showCalendarDialog() {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(Objects.requireNonNull(AddAlarmsActivity.this), R.style.datepicker,
                (view, year, monthOfYear, dayOfMonth) -> {

                    c.setTimeInMillis(0);
                    c.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                    Date chosenDate = c.getTime();

                    tv_date.setText(formatToDisplayMessageFormat_2(chosenDate));


                }, mYear, mMonth, mDay);
        datePickerDialog.setTitle("");
        datePickerDialog.getDatePicker().setMinDate((System.currentTimeMillis()));
        datePickerDialog.show();
    }

    public static String formatToDisplayMessageFormat_2(Date date) {
        if (date == null) {
            return null;
        }
        String dateFormat = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        return simpleDateFormat.format(date);
    }


    // يتم من خلاها فحص ال edit اذا فاضي او لا
    public static boolean ValidationEmptyInput(EditText text) {
        if (TextUtils.isEmpty(text.getText().toString())) {
            return false;
        }
        return true;

    }


    private void setAlarm(String textTask, String date, String time) {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);                   //assigning alarm manager object to set alarm
        Intent intent = new Intent(getApplicationContext(), AlarmsReceiver.class);
        intent.putExtra("task", textTask);                                                       //sending data to alarm class to create channel and notification
        intent.putExtra("timeTask", time);
        intent.putExtra("dateTask", date);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String timeAlarm = date + " " + timeSelect;
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            Date dateFormatter = formatter.parse(timeAlarm);
            am.set(AlarmManager.RTC_WAKEUP, dateFormatter.getTime(), pendingIntent);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Alert Added Successfully", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(AddAlarmsActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }
}