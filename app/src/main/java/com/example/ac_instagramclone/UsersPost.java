package com.example.ac_instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UsersPost extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_post);

        Intent recivedIntentObject= getIntent();
        String recivedUserName= recivedIntentObject.getStringExtra("username");
        Toast.makeText(this,recivedUserName,Toast.LENGTH_SHORT).show();
    }
}
