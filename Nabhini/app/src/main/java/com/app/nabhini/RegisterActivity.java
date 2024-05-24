package com.app.nabhini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.app.nabhini.DB.DataBase;
import com.app.nabhini.Modle.User;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username, password, name, phone;
    private Button  btnDone;
    private ImageView imageBack ;

    private DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findView();

    }

    private void findView() {

        db = new DataBase(this);
        name = findViewById(R.id.name);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        btnDone = findViewById(R.id.btnDone);
        imageBack = findViewById(R.id.imageBack);

        btnDone.setOnClickListener(this);
        imageBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imageBack:
                onBackPressed();
                break;

            case R.id.btnDone:
                // فحص القيم اذا كانت موجودة او لا و يتم فتح الداتا بيس لادخال بيانات اليوزر اذا تمت العملية تم تسجيل اليوزر بنجاح والارسال الي واجهة اللوجن و اذا لم يتم لم تكتمل عملية التسجيل
                if (validation()) {
                    db.open();
                    boolean isDone = db.InsertUSER(new User(name.getText().toString(), username.getText().toString(), phone.getText().toString()), password.getText().toString());
                    db.close();
                    if (isDone) {
                        Toast.makeText(this, "You Have Been Successfully Registered", Toast.LENGTH_SHORT).show();
                        // الانتقال الي واجهة تسجيل الدخول
                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();

                    } else {
                        Toast.makeText(RegisterActivity.this, "The Registration Process Failed", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    // هذه الدالة تقوم بقحص جميع المدخلات اذا موجودة او لا
    private boolean validation() {

        if (!ValidationEmptyInput(name)) {
            Toast.makeText(this, "Full Name is Empty", Toast.LENGTH_LONG).show();

            return false;

        } else if (!ValidationEmptyInput(username)) {
            Toast.makeText(this, "User Name is Empty", Toast.LENGTH_LONG).show();

            return false;

        } else if (!ValidationEmptyInput(phone)) {
            Toast.makeText(this, "Phone is Empty", Toast.LENGTH_LONG).show();

            return false;

        } else if (phone.getText().toString().length() < 8) {
            Toast.makeText(this, "The Phone Number Must Be More Than 8 Digits", Toast.LENGTH_LONG).show();

            return false;

        } else if (!ValidationEmptyInput(password)) {
            Toast.makeText(this, "Password is Empty", Toast.LENGTH_LONG).show();

            return false;

        } else if (password.getText().toString().length() < 6) {
            Toast.makeText(this, "The Password Must Be At Least 6 Digits", Toast.LENGTH_LONG).show();

            return false;

        } else {
            return true;

        }
    }

    // يتم من خلاها فحص ال edit اذا فاضي او لا
    public static boolean ValidationEmptyInput(EditText text) {
        if (TextUtils.isEmpty(text.getText().toString())) {
            return false;
        }
        return true;

    }
}
