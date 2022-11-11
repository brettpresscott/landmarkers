package com.example.maplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splashy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashy);

        int DISPLAY_LENGTH = 3000;
        new Handler().postDelayed(()-> {
            //CREATE INTENT
            Intent splashIntent = new Intent(Splashy.this, LoginActivity.class);
            startActivity(splashIntent);
            this.finish();
        }, DISPLAY_LENGTH);
    }
}