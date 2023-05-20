package com.example.libraryapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class StartMenu extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_menu);

        TextView textView = findViewById(R.id.textView3);
        textView.setText("Текущ потребител" + PreferenceManager.getID(this));

        Button borrow = findViewById(R.id.borrow);
        borrow.setOnClickListener(v -> {
            Intent intent = new Intent(StartMenu.this, BorrowActivity.class);
            startActivity(intent);
        });
        Button returns = findViewById(R.id.returns);
        returns.setOnClickListener(v -> {
            Intent intent = new Intent(StartMenu.this, ReturnActivity.class);
            startActivity(intent);
        });
        Button history = findViewById(R.id.history);
        history.setOnClickListener(v -> {
            Intent intent = new Intent(StartMenu.this, HistoryActivity.class);
            startActivity(intent);
        });


        Button signOut = findViewById(R.id.sign_out);

        signOut.setOnClickListener(v ->

        {
//            PreferenceManager.clearFreeToTakeBooks(this);
            PreferenceManager.clearID(this);
            Intent intent = new Intent(StartMenu.this, MainActivity.class);
            startActivity(intent);
        });

    }
}
