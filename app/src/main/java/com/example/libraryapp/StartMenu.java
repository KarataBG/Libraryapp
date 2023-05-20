package com.example.libraryapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StartMenu extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_menu);

        //Изписва текущия потребител
        TextView textView = findViewById(R.id.textView3);
        textView.setText("Текущ потребител" + PreferenceManager.getID(this));

        //Възлага бутоните, които придвижват сесията
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
        signOut.setOnClickListener(v -> {
            PreferenceManager.clearID(this);
            Intent intent = new Intent(StartMenu.this, MainActivity.class);
            startActivity(intent);
        });

    }
}
