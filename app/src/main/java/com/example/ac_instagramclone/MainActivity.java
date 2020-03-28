package com.example.ac_instagramclone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //UI Components:
    private EditText userIdEnter,userPassword;
    private Button signIn,getbutton;
    private String allBoxer;
    private ParseObject boxer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userIdEnter=findViewById(R.id.userIdEnter);
        userPassword=findViewById(R.id.userPassword);
        signIn=findViewById(R.id.signIn);
        getbutton=findViewById(R.id.button2);

        //signIn.setOnClickListener();

        getbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                allBoxer="";
                ParseQuery<ParseObject> allQuery=ParseQuery.getQuery("boxer") ;
        /*allQuery.getInBackground("uHZdFPFGy6", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if(object !=null&& e==null){
                    Toast.makeText(MainActivity.this,object.get("UserId")+"",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"hhh+",Toast.LENGTH_LONG).show();
                }
            }
        });*/

                allQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e==null){
                            for(ParseObject boxer : objects){
                                allBoxer=allBoxer+boxer.get("UserId")+"\n";
                            }
                            Toast.makeText(MainActivity.this,allBoxer+"",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }


    @Override
    public void onClick(View v) {

        boxer=new ParseObject("boxer");
        boxer.put("UserId",userIdEnter.getText().toString());
        boxer.put("password",userPassword.getText().toString());

        boxer.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                //if(e==null){
                    Toast.makeText(MainActivity.this,"Sucess",Toast.LENGTH_LONG).show();
              //  }
            }
        });
    }

}
