package com.example.ac_instagramclone;
import com.parse.Parse;
import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("6y6iOy7nsvuY8juwSJa9mBY5GZOmifzdHr85z139")
                // if desired
                .clientKey("NOoJDVe8f9rs2R1nLfnlswNHEZUNK34laoXupFNT")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
