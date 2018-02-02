package com.example.rilu_pc.shoppyfy;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity
{
    EditText fname, user,pass;
    ImageView dp;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    private Button upload;
    public static final String Name = "fname";
    public static final String User = "email";
    public static final String Pass = "pswd";
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        upload = (Button) findViewById(R.id.upload);
        fname = findViewById(R.id.fname);
        user = findViewById(R.id.email);
        pass  = findViewById(R.id.pswd);
        upload.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                selectImage();
            }
        });
        dp= (ImageView) findViewById(R.id.dp);
        sharedpreferences = getApplicationContext().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }

    private void selectImage()
    {
        final CharSequence[] items = { "Camera", "Gallery" };
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
       // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item)
            {
                boolean result=Utility.checkPermission(RegisterActivity.this);
                if (items[item].equals("Camera"))
                {
                    userChoosenTask="Camera";
                    if(result)
                        cameraIntent();
                }
                else if (items[item].equals("Gallery"))
                {
                    userChoosenTask="Gallery";
                    if(result)
                        galleryIntent();
                }
//                else if (items[item].equals("Cancel"))
//                {
//                    dialog.dismiss();
//                }
            }
        });
        builder.show();
    }
    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }
    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data)
    {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        dp.setImageBitmap(bm);
    }


    private void onCaptureImageResult(Intent data)
    {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try
        {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        dp.setImageBitmap(thumbnail);
    }



    public void log_back(View s)
    {
        String f = fname.getText().toString();
        String email =user.getText().toString();
        String pswd = pass.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        //to store information

        editor.putString(Name, f);
        editor.putString(User, email);
        editor.putString(Pass,pswd);
        editor.commit();
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
    }
}
