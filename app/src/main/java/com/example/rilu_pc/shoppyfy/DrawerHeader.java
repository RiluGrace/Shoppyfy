package com.example.rilu_pc.shoppyfy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import static com.example.rilu_pc.shoppyfy.LoginActivity.User;

/**
 * Created by babur on 16-01-2018.
 */

@NonReusable
@Layout(R.layout.drawer_header)


public class DrawerHeader
{
    @View(R.id.profileImageView)
    private ImageView profileImage;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Email = "email";
    public static final String Name = "fname";
    @View(R.id.nameTxt)
    private TextView nameTxt;
    Context context;

    @View(R.id.emailTxt)
    private TextView emailTxt;



    public DrawerHeader(Context mContext) {
        context=mContext;
        sharedpreferences=context.getApplicationContext().getSharedPreferences(mypreference, 0);
    }

    @Resolve
    private void onResolved() {


        Log.d("user...",sharedpreferences.getString(User, ""));

        if (sharedpreferences.contains(Name))
        {
            nameTxt.setText(sharedpreferences.getString(Name, ""));
            sharedpreferences.getString("fname", null); // getting String

        }
        if (sharedpreferences.contains(Email))
        {
            emailTxt.setText(sharedpreferences.getString(User, ""));
            sharedpreferences.getString("email", null); // getting String

        }
        // jst to print this
     //   emailTxt.setText("rilugrace95@gmail.com");
    }
}

