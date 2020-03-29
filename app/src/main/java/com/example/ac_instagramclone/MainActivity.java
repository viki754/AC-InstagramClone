package com.example.ac_instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //UI Components:
    private EditText userIdEnter,userPassword;
    private Button signIn,signUp;
    private String allBoxer;
    private ParseObject boxer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userIdEnter = findViewById(R.id.userIdEnter);
        userPassword = findViewById(R.id.userPassword);
        signIn = findViewById(R.id.signIn);
        // getbutton=findViewById(R.id.getIt);
        signUp = findViewById(R.id.signUp);

        if (ParseUser.getCurrentUser() != null) {
            transitionToSocialMediaActivity();
        }




        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,SignUpAct.class);
                startActivity(intent);

            }
        });

    }


    @Override
    public void onClick(View v) {

        ParseUser.logInInBackground(userIdEnter.getText().toString(),
                userPassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user!=null && e==null){
                            Toast.makeText(MainActivity.this,"Login Sucess",Toast.LENGTH_LONG).show();
                            transitionToSocialMediaActivity();

                        }else{
                            Toast.makeText(MainActivity.this,"Not a user Please signin",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
    public void LogInOnClick(View view){
        try {
            InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void transitionToSocialMediaActivity(){
        Intent intent =new Intent(MainActivity.this,SocialMediaActivity.class);
        startActivity(intent);
    }

}
