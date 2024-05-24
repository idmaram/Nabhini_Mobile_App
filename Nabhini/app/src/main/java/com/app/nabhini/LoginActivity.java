package com.app.nabhini;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.nabhini.DB.DataBase;
import com.app.nabhini.Modle.User;
import com.app.nabhini.utils.SharedPreferenceApp;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username, password;
    private Button btnLogin;
    private TextView toRegister;

    private DataBase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        findView();

    }

    private void findView() {

        db = new DataBase(this);

        //  فحص اذا كانت في قيمة مخزنة في ال SharedPreference يروح على الواجهة الرئيسية
        if (SharedPreferenceApp.getInstance(this).getNumber("idLogin", 0) > 0) {
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();

        }

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        toRegister = findViewById(R.id.toRegister);

        btnLogin.setOnClickListener(this);
        toRegister.setOnClickListener(this);



    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                // فحص القيم اذا كانت موجودة او لا و يتم فتح الداتا بيس و الدخال البيانات في الداتا بيس سواء كانت بيانات اليورز او بيانات المنيو و المنتجات الخاصة فيها و يتم في كل تسجيل الدخول حذف البيانات و ادخالها من جديد حتى ما بيصير تكرار في البانات و اذا كانت في غلط في البيانات يطبع في خطأ في اسم اليوزر او كلمة المرور
                if (validation()) {
                    db.open();
                    User user = db.Login(username.getText().toString(), password.getText().toString());
                    db.close();
                    if (user != null) {

                        // SharedPreference الانتقال الي الواجهة الرئيسية و حقظ قيمة id_user في ال
                        Toast.makeText(this, "You Have Been Successfully Signed In", Toast.LENGTH_SHORT).show();
                        SharedPreferenceApp.getInstance(LoginActivity.this).saveNumber("idLogin", user.getId());
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        finish();

                    } else {
                        Toast.makeText(LoginActivity.this, "Username or password is wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            case R.id.toRegister:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
        }
    }


    // هذه الدالة تقوم بقحص جميع المدخلات اذا موجودة او لا
    private boolean validation() {
        if (!ValidationEmptyInput(username)) {
            Toast.makeText(this, "User Name is Empty", Toast.LENGTH_LONG).show();

            return false;

        } else if (!ValidationEmptyInput(password)) {
            Toast.makeText(this, "Password is Empty", Toast.LENGTH_LONG).show();

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