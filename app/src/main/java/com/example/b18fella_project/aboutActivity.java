package com.example.b18fella_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class aboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent aboutActivity = getIntent();

        String aboutMessage = aboutActivity.getStringExtra(MainActivity.X_MESSAGE);

        TextView textView = findViewById(R.id.aboutText);

        textView.setText(aboutMessage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
