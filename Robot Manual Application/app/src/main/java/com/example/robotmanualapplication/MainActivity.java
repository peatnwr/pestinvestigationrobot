package com.example.robotmanualapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton forwardBtn = findViewById(R.id.forwardBtn);
        FloatingActionButton turnLeftBtn = findViewById(R.id.turnLeftBtn);
        FloatingActionButton turnRightBtn = findViewById(R.id.turnRightBtn);
        FloatingActionButton backwardBtn = findViewById(R.id.backwardBtn);
        Button stopBtn = findViewById(R.id.stopBtn);

        forwardBtn.setOnClickListener(
                view -> Log.e("forward Button", "Clicked")
        );

        turnLeftBtn.setOnClickListener(
                view -> Log.e("turnLeft Button", "Clicked")
        );

        turnRightBtn.setOnClickListener(
                view -> Log.e("turnRight Button", "Clicked")
        );

        backwardBtn.setOnClickListener(
                view -> Log.e("backward Button", "Clicked")
        );

        stopBtn.setOnClickListener(
                view -> Log.e("stop Button", "Clicked")
        );

    }
}