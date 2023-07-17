package com.example.highmusicapp.ActivityController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.highmusicapp.R;

public class ChatActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Button chat_btn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        preferences = getSharedPreferences("MIA", MODE_PRIVATE);
        editor = preferences.edit();
        chat_btn = findViewById(R.id.chat_btn);

        if (preferences.contains("username")) {
            Toast.makeText(this, "Welcome " + preferences.getString("username", ""), Toast.LENGTH_SHORT).show();
        }

        chat_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChatActivity.this, "We will develop this in future :>", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChatActivity.this, ViewProductActivity.class);
                startActivity(intent);
            }
        });
    }
}