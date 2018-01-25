package com.example.rilu_pc.shoppyfy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class LoginActivity extends AppCompatActivity {

    EditText et_username, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
    }

    public void login_now(View v) {
        String u = et_username.getText().toString();
        String p = et_password.getText().toString();


        if (!u.equals("")) {
            if (!p.equals("")) {
                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(i);
            } else {
                Toast.makeText(this, "password can't be empty", Toast.LENGTH_LONG).show();
            }

        } else {

            Toast.makeText(this, "username can't be empty", Toast.LENGTH_LONG).show();
        }



//    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
//    Date currentLocalTime = cal.getTime();
//    DateFormat date = new SimpleDateFormat("HH:mm a");
//// you can get seconds by adding  "...:ss" to it
//        date.setTimeZone(TimeZone.getTimeZone("GMT+1:00"));
//
//
//    String localTime = date.format(currentLocalTime);
}
    public void reg_now(View r)
    {
        Toast.makeText(this,"time",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }

}
