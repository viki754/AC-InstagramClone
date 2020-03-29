package com.example.ac_instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpAct extends AppCompatActivity  implements View.OnClickListener {


    private EditText signupName,signupPassword,signupEmail;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUp=findViewById(R.id.signUp);
        signupName=findViewById(R.id.signupName);
        signupPassword=findViewById(R.id.signupPassword);

        signupPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
                    onClick(signUp);
                }
                return false;
            }
        });

        signupEmail=findViewById(R.id.signupEmail);

        if(ParseUser.getCurrentUser() != null){
            transitionToSocialMediaActivity();
        }
    }

    @Override
    public void onClick(View v) {


        if(signupEmail.getText().toString().equals("")||
                signupName.getText().toString().equals("")||
                signupPassword.getText().toString().equals("")){
            Toast.makeText(SignUpAct.this,"Email,User name,Password Required",Toast.LENGTH_LONG).show();
        }else{
            ParseUser user=new ParseUser();
            user.setEmail(signupEmail.getText().toString());
            user.setUsername(signupName.getText().toString());
            user.setPassword(signupPassword.getText().toString());

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e==null){
                        Toast.makeText(SignUpAct.this, "SignUp Sucessfull", Toast.LENGTH_SHORT).show();
                        transitionToSocialMediaActivity();

                    }else{
                        Toast.makeText(SignUpAct.this, "Try Again Later", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    public void signUpOnClick(View view){
        try {
            InputMethodManager inputMethodManager=(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void transitionToSocialMediaActivity(){
        Intent intent =new Intent(SignUpAct.this,SocialMediaActivity.class);
        startActivity(intent);
    }

    }

