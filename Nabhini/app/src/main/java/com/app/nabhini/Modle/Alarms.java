package com.app.nabhini.Modle;

public class Alarms {

    private int id;
    private int id_user;
    private String title_alarm;
    private String date;
    private String time;

    public static final String TABLE_NAME = "alarms";
    public static final String COL_ID = "id";
    public static final String COL_ID_user = "id_user";
    public static final String COL_Title = "title_alarm";
    public static final String COL_Date = "date";
    public static final String COL_TIME = "time";

    // الجدول الخاص بالتذكيرات
    public static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME+
            "("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            COL_ID_user +" INTEGER,"+
            COL_Title +" TEXT,"+
            COL_Date +" TEXT,"+
            COL_TIME +" TEXT )";


    public Alarms() {
    }

    public Alarms( int id_user, String title_alarm, String date, String time) {
        this.id_user = id_user;
        this.title_alarm = title_alarm;
        this.date = date;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle_alarm() {
        return title_alarm;
    }

    public void setTitle_alarm(String title_alarm) {
        this.title_alarm = title_alarm;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
