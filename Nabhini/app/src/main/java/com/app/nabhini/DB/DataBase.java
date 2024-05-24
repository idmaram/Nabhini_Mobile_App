package com.app.nabhini.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.app.nabhini.Modle.Alarms;
import com.app.nabhini.Modle.User;

import java.util.ArrayList;
import static android.content.ContentValues.TAG;


public class DataBase extends SQLiteOpenHelper {
    private SQLiteDatabase database;

    // انشاء جدول اليوزر
    private static final String CREATE_TABLE_USER =
            "create table " + Constants.TABLE_USER + " (" +
                    Constants.COL_ID + " integer primary key autoincrement, " +
                    Constants.COL_NAME + " text ," +
                    Constants.COL_USER_NAME + " text ," +
                    Constants.COL_PHONE + " text," +
                    Constants.COL_PASSWORD + " text," +
                    Constants.COL_IMAGE_CREDIT + " BLOB " +
                    " );";


    public DataBase(Context context) {
        super(context, "menu.db", null, 1);
    }

    public void onCreate(SQLiteDatabase database) {
        // ادراج الجداول
        database.execSQL(CREATE_TABLE_USER);
        database.execSQL(Alarms.CREATE_TABLE);

    }

    public void open() throws SQLException {
        database = getWritableDatabase();
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // حذف الجدول
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + Alarms.TABLE_NAME);

        onCreate(db);
    }

    interface Constants {
        String COL_ID = "id";
        String TABLE_USER = "USER";
        String COL_NAME = "name";
        String COL_USER_NAME = "user_name";
        String COL_PHONE = "phone";
        String COL_PASSWORD = "password";
        String COL_IMAGE_CREDIT = "image_credit";
    }

    // اضافة يورز جديد
    public boolean InsertUSER(User item, String password) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();
            initialValues.put(Constants.COL_USER_NAME, item.getUsername());
            initialValues.put(Constants.COL_PASSWORD, password);
            initialValues.put(Constants.COL_NAME, item.getName());
            initialValues.put(Constants.COL_PHONE, item.getPhone());
            initialValues.put(Constants.COL_USER_NAME, item.getUsername());
            didSucceed = database.insert(Constants.TABLE_USER, null, initialValues) > 0;

        } catch (Exception e) {
        }
        return didSucceed;
    }

    // عمل لوجن لليوزر
    @SuppressLint("Range")
    public User Login(String username, String password) {
        try {
            String query = "SELECT  * FROM  " + Constants.TABLE_USER + " where " + Constants.COL_USER_NAME + " like '" + username + "' and " + Constants.COL_PASSWORD + " like '" + password + '\'';
            Cursor cursor = database.rawQuery(query, null);
            if (cursor.getColumnCount() == 0) {
                return null;
            }
            User data;
            cursor.moveToFirst();
            data = new User();
            data.setId(cursor.getInt(cursor.getColumnIndex(Constants.COL_ID)));
            data.setName(cursor.getString(cursor.getColumnIndex(Constants.COL_NAME)));
            data.setUsername(cursor.getString(cursor.getColumnIndex(Constants.COL_USER_NAME)));
            data.setPhone(cursor.getString(cursor.getColumnIndex(Constants.COL_PHONE)));
            cursor.close();
            return data;
        } catch (Exception e) {
            Log.i(TAG, "getLogin: " + e.getMessage());
        }
        return null;
    }


    // اضافة تذكير
    public boolean insertAlarms(Alarms alarms) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Alarms.COL_ID_user, alarms.getId_user());
        contentValues.put(Alarms.COL_Title, alarms.getTitle_alarm());
        contentValues.put(Alarms.COL_Date, alarms.getDate());
        contentValues.put(Alarms.COL_TIME, alarms.getTime());
        return database.insert(Alarms.TABLE_NAME, null, contentValues) > 0;
    }

    // عرض الطلبات حسب يوزر محدد
    @SuppressLint("Range")
    public ArrayList<Alarms> getAlarms(int id_user) {
        ArrayList<Alarms> alarms = new ArrayList<>();

        String sqlquery = "SELECT * FROM " + Alarms.TABLE_NAME + " WHERE id_user = " + id_user;
        Cursor cursor = database.rawQuery(sqlquery, null);
        if (cursor.moveToFirst()) {
            do {
                Alarms alarms1 = new Alarms();
                alarms1.setId_user(cursor.getInt(cursor.getColumnIndex(Alarms.COL_ID_user)));
                alarms1.setTitle_alarm(cursor.getString(cursor.getColumnIndex(Alarms.COL_Title)));
                alarms1.setDate(cursor.getString(cursor.getColumnIndex(Alarms.COL_Date)));
                alarms1.setTime(cursor.getString(cursor.getColumnIndex(Alarms.COL_TIME)));
                alarms.add(alarms1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return alarms;
    }



}
