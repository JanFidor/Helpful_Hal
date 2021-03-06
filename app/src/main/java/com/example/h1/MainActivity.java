package com.example.h1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.h1.AssigmentActivity.TaskList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.DarkTheme);
        setContentView(R.layout.activity_main);

        ImageView button_assignment = findViewById(R.id.assignment);
        button_assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TaskList.class);
                v.getContext().startActivity(intent);
            }
        });

        ImageView button_pomodoro = findViewById(R.id.PomodoroActivity);
        button_pomodoro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PomodoroManager.class);
                v.getContext().startActivity(intent);
            }
        });
    }
}