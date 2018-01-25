package com.example.rilu_pc.shoppyfy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class LoginHistoryActivity extends AppCompatActivity
{

    Calendar calander;
    SimpleDateFormat simpledateformat;
    String Date;
    TextView DisplayDateTime;
    Button BTN;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_history);
        DisplayDateTime = (TextView)findViewById(R.id.tv_time);

        BTN = (Button)findViewById(R.id.button1);

        calander = Calendar.getInstance();
        simpledateformat = new SimpleDateFormat(" HH:mm:ss a");
        Date = simpledateformat.format(calander.getTime());
        final DatabaseHandler db = new DatabaseHandler(this);


        BTN.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DisplayDateTime.setText(Date);
                db.addTime(new Time(DisplayDateTime.getText().toString()));
                List<Time> contacts = db.getAllTime();
                for (Time cn : contacts)
                {
                    String log = "Time: " + cn.get_time();
                    // Writing Contacts to log
                    Log.d("time: ", log);
                }

            }
        });
    }
    }


