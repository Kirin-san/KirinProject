package com.example.kirinproject;

import android.app.Application;

import com.parse.Parse;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("H6IYaVFsmuTDbiDgUKn3")
                // if defined
                .clientKey("5xHY2cYDhvOR4yVYyIas")
                .server("https://arcane-plains-57753.herokuapp.com/parse/")
                .build()
        );
    }
}