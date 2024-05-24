package com.app.nabhini;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.AlarmManagerCompat;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.nabhini.Adapter.AlarmAdapter;
import com.app.nabhini.BroadCast.AlarmsReceiver;
import com.app.nabhini.DB.DataBase;
import com.app.nabhini.Modle.Alarms;
import com.app.nabhini.utils.SharedPreferenceApp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton btnAdd, btnLogOut;
    private RecyclerView rv_task;

    private List<Alarms> alarms;
    private DataBase database;
    private AlarmAdapter alarmAdapter;
    private int id_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        

    }

    private void findView() {

        database = new DataBase(this);

        alarms = new ArrayList<>();
        id_user = SharedPreferenceApp.getInstance(this).getNumber("idLogin", 0);

        rv_task = findViewById(R.id.rv_task);
        btnAdd = findViewById(R.id.btnAdd);
        btnLogOut = findViewById(R.id.btnLogOut);

        btnAdd.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);

        database.open();
        alarms = database.getAlarms(id_user);

        rv_task.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        alarmAdapter = new AlarmAdapter(MainActivity.this, alarms);
        rv_task.setAdapter(alarmAdapter);

        database.close();


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                startActivity(new Intent(MainActivity.this, AddAlarmsActivity.class));
                break;

            case R.id.btnLogOut:
                Toast.makeText(this, "You Have Been Logged Out Successfully", Toast.LENGTH_SHORT).show();
                SharedPreferenceApp.getInstance(MainActivity.this).clear();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }
}
