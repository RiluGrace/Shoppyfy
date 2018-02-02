package com.example.rilu_pc.shoppyfy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class LoginActivity extends AppCompatActivity
{

    EditText et_username, et_password;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String User = "user";
    public static final String Pass = "pass";
    public static final String Email= "email";
    public static final String Password="pswd";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);

    }

    public void login_now(View v) {
        String u = et_username.getText().toString();
        String p = et_password.getText().toString();
        // String email =user.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        //to store information

        editor.putString(User, u);
        editor.putString(Pass, p);
        editor.commit();
        String email = sharedpreferences.getString(Email, "");
        String pswd =  sharedpreferences.getString(Password,"");
        Log.d("user...", sharedpreferences.getString(Email, ""));
        Log.d("user..", "log");
        sharedpreferences.getString("user", null); // getting String
        if (!u.equals("")) {
            if (u.equals(email)) {
                if (!p.equals("")) {
                    if (p.equals(pswd)) {
                        Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(this, "password is invalid", Toast.LENGTH_LONG).show();
                    }
                }
                else{ Toast.makeText(this, "password can't be empty", Toast.LENGTH_LONG).show();}
            }
             else{
                    Toast.makeText(this, "username is invalid", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "username can't be empty", Toast.LENGTH_LONG).show();
            }
        }

    public void reg_now(View r)
    {
        Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(i);
    }

}
